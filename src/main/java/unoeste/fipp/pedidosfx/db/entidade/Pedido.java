package unoeste.fipp.pedidosfx.db.entidade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    static public record Item(Produto produto, int quant, double valor){};
    private int id;
    private LocalDate data;
    private String nomeCliente;
    private String foneCliente;
    private double total;
    private char viagem; //S ou N
    private TipoPagamento tipoPagamento;
    private List<Item> itens;


    public Pedido(int id, LocalDate data, String nomeCliente, String foneCliente, double total, char viagem, TipoPagamento tipoPagamento) {
        this.id = id;
        this.data = data;
        this.nomeCliente = nomeCliente;
        this.foneCliente = foneCliente;
        this.total = total;
        this.viagem = viagem;
        this.tipoPagamento = tipoPagamento;
        itens=new ArrayList<>();
    }

    public Pedido(LocalDate data, String nomeCliente, String foneCliente, double total, char viagem, TipoPagamento tipoPagamento) {
        this(0,data,nomeCliente,foneCliente,total,viagem,tipoPagamento);
    }

    public Pedido() {
        this(0,LocalDate.now(),"","",0,'N',null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getFoneCliente() {
        return foneCliente;
    }

    public void setFoneCliente(String foneCliente) {
        this.foneCliente = foneCliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public char getViagem() {
        return viagem;
    }

    public void setViagem(char viagem) {
        this.viagem = viagem;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    public boolean addItem(Produto produto, int quant)
    {
        return itens.add(new Item(produto,quant,produto.getValor()));
    }

    public boolean addItem(Item item)
    {
        return itens.add(item);
    }

    public List<Item> getItens() {
        return itens;
    }

    public void totalizar()
    {
        double tot=0;
        for(Item item: itens){
            tot+=item.valor()*item.quant();
        }
        this.total=tot;
    }
}
