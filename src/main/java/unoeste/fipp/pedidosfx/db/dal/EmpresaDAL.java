package unoeste.fipp.pedidosfx.db.dal;

import unoeste.fipp.pedidosfx.db.entidade.Empresa;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAL implements IDAL<Empresa> {
    @Override
    public boolean gravar(Empresa entidade) {
        String sql = """
                INSERT INTO empresa(
                     emp_razao, emp_fantasia, emp_cnpj, emp_cep, emp_rua, emp_numero, emp_bairro, emp_cidade, emp_uf, emp_fone, emp_email, emp_vlremb)
                	VALUES ('#1', '#2', '#3', '#4', '#5', '#6', '#7', '#8', '#9','#A10','#A11', #A12);
                """;
        sql = sql.replace("#1", entidade.getRazaoSocial());
        sql = sql.replace("#2", entidade.getNomeFantasia());
        sql = sql.replace("#3", entidade.getCnpj());
        sql = sql.replace("#4", entidade.getCep());
        sql = sql.replace("#5", entidade.getRua());
        sql = sql.replace("#6", entidade.getNumeroDaRua());
        sql = sql.replace("#7", entidade.getBairro());
        sql = sql.replace("#8", entidade.getCidade());
        sql = sql.replace("#9", entidade.getUf());
        sql = sql.replace("#A10", entidade.getTelefone());
        sql = sql.replace("#A11", entidade.getEmail());
        sql = sql.replace("#A12", String.valueOf(entidade.getValorDaEmbalagem()));

        System.out.println("Gravando query: " + sql);

        return SingletonDB.getConexao().manipular(sql);

    }

    @Override
    public boolean alterar(Empresa entidade) {
        String sql = """
                UPDATE empresa
                SET emp_razao = '#1',
                emp_fantasia = '#2',
                emp_cnpj = '#3',
                emp_cep = '#4',
                emp_rua = '#5',
                emp_numero = '#6',
                emp_bairro = '#7',
                emp_cidade = '#8',
                emp_uf = '#9',
                emp_fone = '#A10',
                emp_email = '#A11',
                emp_vlremb = '#A12'
                WHERE emp_id = #A13;
                """;
        sql = sql.replace("#1", entidade.getRazaoSocial());
        sql = sql.replace("#2", entidade.getNomeFantasia());
        sql = sql.replace("#3", entidade.getCnpj());
        sql = sql.replace("#4", entidade.getCep());
        sql = sql.replace("#5", entidade.getRua());
        sql = sql.replace("#6", entidade.getNumeroDaRua());
        sql = sql.replace("#7", entidade.getBairro());
        sql = sql.replace("#8", entidade.getCidade());
        sql = sql.replace("#9", entidade.getUf());
        sql = sql.replace("#A10", entidade.getTelefone());
        sql = sql.replace("#A11", entidade.getEmail());
        sql = sql.replace("#A12", String.valueOf(entidade.getValorDaEmbalagem()));
        sql = sql.replace("#A13", String.valueOf(entidade.getId()));
        System.out.println("Alterando query: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Empresa entidade) {

        return SingletonDB.getConexao().manipular("DELETE FROM empresa WHERE emp_id = " + entidade.getId());
    }

    @Override
    public Empresa get(int id) {
        Empresa empresa = null;
        String sql = "SELECT * FROM empresa WHERE emp_id = " + id;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try {
            if (resultSet.next()) {
                empresa = new Empresa(resultSet.getInt("emp_id"), resultSet.getString("emp_razao"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_cep"),
                        resultSet.getString("emp_rua"),
                        resultSet.getString("emp_numero"),
                        resultSet.getString("emp_bairro"),
                        resultSet.getString("emp_cidade"),
                        resultSet.getString("emp_uf"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_vlremb"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empresa;
    }

    @Override
    public List<Empresa> get(String filtro) {
        List<Empresa> listaDeEmpresas = new ArrayList<Empresa>();
        Empresa empresa;
        String sql = "SELECT * FROM empresa";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }

        try {
            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
            while (resultSet.next()) {
                empresa = new Empresa(resultSet.getInt("emp_id"), resultSet.getString("emp_razao"),
                        resultSet.getString("emp_fantasia"),
                        resultSet.getString("emp_cnpj"),
                        resultSet.getString("emp_cep"),
                        resultSet.getString("emp_rua"),
                        resultSet.getString("emp_numero"),
                        resultSet.getString("emp_bairro"),
                        resultSet.getString("emp_cidade"),
                        resultSet.getString("emp_uf"),
                        resultSet.getString("emp_fone"),
                        resultSet.getString("emp_email"),
                        resultSet.getDouble("emp_vlremb"));
                listaDeEmpresas.add(empresa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDeEmpresas;

    }
}
