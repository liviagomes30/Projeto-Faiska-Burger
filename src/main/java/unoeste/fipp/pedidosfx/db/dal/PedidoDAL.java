package unoeste.fipp.pedidosfx.db.dal;

import unoeste.fipp.pedidosfx.db.entidade.Pedido;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAL implements IDAL<Pedido> {
    @Override
    public boolean gravar(Pedido entidade) {
        boolean erro = false;
        try {
            SingletonDB.getConexao().getConnect().setAutoCommit(false);

            String sql = """
                     INSERT INTO pedido(ped_data, ped_clinome,
                     ped_clifone, ped_total, ped_viagem, tpg_id)
                     VALUES ('#1', '#2', '#3', #4, '#5', #6);
                    """;
            sql = sql.replace("#1", entidade.getData().toString());
            sql = sql.replace("#2", entidade.getNomeCliente());
            sql = sql.replace("#3", entidade.getFoneCliente());
            sql = sql.replace("#4", "" + entidade.getTotal());
            sql = sql.replace("#5", "" + entidade.getViagem());
            sql = sql.replace("#6", "" + entidade.getTipoPagamento().getId());
            if (SingletonDB.getConexao().manipular(sql)) {
                int id = SingletonDB.getConexao().getMaxPK("pedido", "ped_id");
                for (Pedido.Item item : entidade.getItens()) {
                    String sql2 = """
                            INSERT INTO item(pro_id, ped_id, it_quant, it_valor)
                            VALUES (#1, #2, #3, #4);
                            """;
                    sql2 = sql2.replace("#1", "" + item.produto().getId());
                    sql2 = sql2.replace("#2", "" + id);
                    sql2 = sql2.replace("#3", "" + item.quant());
                    sql2 = sql2.replace("#4", "" + item.valor());
                    if (!SingletonDB.getConexao().manipular(sql2)) {
                        erro = true;
                    }
                }
            } else
                erro = true;
            if (erro) {
                SingletonDB.getConexao().getConnect().rollback();
            } else
                SingletonDB.getConexao().getConnect().commit();
            SingletonDB.getConexao().getConnect().setAutoCommit(true);
        } catch (Exception e) {
        }
        return !erro;
    }

    @Override
    public boolean alterar(Pedido entidade) {
        boolean erro = false;
        try {
            SingletonDB.getConexao().getConnect().setAutoCommit(false);

            String sql = """
                     UPDATE public.pedido
                     SET ped_data='#1', ped_clinome='#2', ped_clifone='#3', ped_total=#4, ped_viagem='#5', tpg_id=#6
                     WHERE ped_id=#7;
                    """;
            sql = sql.replace("#1", entidade.getData().toString());
            sql = sql.replace("#2", entidade.getNomeCliente());
            sql = sql.replace("#3", entidade.getFoneCliente());
            sql = sql.replace("#4", "" + entidade.getTotal());
            sql = sql.replace("#5", "" + entidade.getViagem());
            sql = sql.replace("#6", "" + entidade.getTipoPagamento().getId());
            sql = sql.replace("#7", "" + entidade.getId());

            if (SingletonDB.getConexao().manipular(sql)) {
                SingletonDB.getConexao().manipular("DELETE FROM item WHERE ped_id=" + entidade.getId());
                for (Pedido.Item item : entidade.getItens()) {
                    String sql2 = """
                            INSERT INTO item(pro_id, ped_id, it_quant, it_valor)
                            VALUES (#1, #2, #3, #4);
                            """;
                    sql2 = sql2.replace("#1", "" + item.produto().getId());
                    sql2 = sql2.replace("#2", "" + entidade.getId());
                    sql2 = sql2.replace("#3", "" + item.quant());
                    sql2 = sql2.replace("#4", "" + item.valor());
                    if (!SingletonDB.getConexao().manipular(sql2)) {
                        erro = true;
                    }
                }
            } else
                erro = true;
            if (erro) {
                SingletonDB.getConexao().getConnect().rollback();
            } else
                SingletonDB.getConexao().getConnect().commit();
            SingletonDB.getConexao().getConnect().setAutoCommit(true);
        } catch (Exception e) {
        }
        return !erro;
    }

    @Override
    public boolean apagar(Pedido entidade) {
        // apaga primeiro os itens
        SingletonDB.getConexao().manipular("DELETE FROM item WHERE ped_id=" + entidade.getId());
        // apaga o pedido
        return SingletonDB.getConexao().manipular("DELETE FROM pedido WHERE ped_id=" + entidade.getId());
    }

    @Override
    public Pedido get(int id) {
        Pedido pedido = null;
        String sql = "SELECT * FROM pedido WHERE ped_id=" + id;
        try {
            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
            if (resultSet.next()) {
                pedido = new Pedido(resultSet.getInt("ped_id"), resultSet.getDate("ped_data").toLocalDate(),
                        resultSet.getString("ped_clinome"), resultSet.getString("ped_clifone"),
                        resultSet.getDouble("ped_total"),
                        resultSet.getString("ped_viagem").charAt(0),
                        new TipoPagamento(1, "PIX")/* new TipoPagamentoDAL().get(resultSet.getInt("tpg_id")) */);
            }
            String sql2 = "SELECT * FROM item WHERE ped_id=" + pedido.getId();
            ResultSet resultSet2 = SingletonDB.getConexao().consultar(sql2);
            while (resultSet2.next()) {
                Produto produto = new ProdutoDAL().get(resultSet2.getInt("prod_id"));
                produto.setValor(resultSet2.getDouble("it_valor"));
                pedido.addItem(produto, resultSet2.getInt("it_quant"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> get(String filtro) {
        List<Pedido> pedidoList = new ArrayList<>();
        String sql = "SELECT * FROM pedido ";
        if (!filtro.isEmpty())
            sql += " WHERE " + filtro;
        try {
            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
            while (resultSet.next()) {
                Pedido pedido = new Pedido(
                        resultSet.getInt("ped_id"),
                        resultSet.getDate("ped_data").toLocalDate(),
                        resultSet.getString("ped_clinome"),
                        resultSet.getString("ped_clifone"),
                        resultSet.getDouble("ped_total"),
                        resultSet.getString("ped_viagem").charAt(0),
                        new TipoPagamentoDAL().get(resultSet.getInt("tpg_id")));

                String sql2 = "SELECT * FROM item WHERE ped_id=" + pedido.getId();
                ResultSet resultSet2 = SingletonDB.getConexao().consultar(sql2);
                while (resultSet2.next()) {
                    Produto produto = new ProdutoDAL().get(resultSet2.getInt("pro_id"));
                    produto.setValor(resultSet2.getDouble("it_valor"));
                    pedido.addItem(produto, resultSet2.getInt("it_quant"));
                }

                pedidoList.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidoList;
    }
}
