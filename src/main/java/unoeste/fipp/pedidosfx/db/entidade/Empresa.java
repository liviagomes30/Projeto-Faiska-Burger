package unoeste.fipp.pedidosfx.db.entidade;

public class Empresa {
    private int id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String cep;
    private String rua;
    private String numeroDaRua;
    private String bairro;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;
    private double valorDaEmbalagem;

    public Empresa(int id, String razaoSocial, String nomeFantasia, String cnpj, String cep, String rua, String numeroDaRua, String bairro, String cidade, String uf, String telefone, String email, double valorDaEmbalagem) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.cep = cep;
        this.rua = rua;
        this.numeroDaRua = numeroDaRua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.telefone = telefone;
        this.email = email;
        this.valorDaEmbalagem = valorDaEmbalagem;
    }

    public Empresa(String razaoSocial, String nomeFantasia, String cnpj, String cep, String rua, String numeroDaRua, String bairro, String cidade, String uf, String telefone, String email, double valorDaEmbalagem) {
        this(0, razaoSocial, nomeFantasia, cnpj, cep, rua, numeroDaRua, bairro, cidade, uf, telefone, email, valorDaEmbalagem);
    }

    public Empresa() {
        this(0, "", "", "", "", "", "", "", "", "", "", "", 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroDaRua() {
        return numeroDaRua;
    }

    public void setNumeroDaRua(String numeroDaRua) {
        this.numeroDaRua = numeroDaRua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getValorDaEmbalagem() {
        return valorDaEmbalagem;
    }

    public void setValorDaEmbalagem(double valorDaEmbalagem) {
        this.valorDaEmbalagem = valorDaEmbalagem;
    }

    public String toString() {
        return "[" +
                "emp_id=" + id + ", " +
                "emp_razao='" + razaoSocial + "', " +
                "emp_fantasia='" + nomeFantasia + "', " +
                "emp_cnpj='" + cnpj + "', " +
                "emp_cep='" + cep + "', " +
                "emp_rua='" + rua + "', " +
                "emp_numero='" + numeroDaRua + "', " +
                "emp_bairro='" + bairro + "', " +
                "emp_cidade='" + cidade + "', " +
                "emp_uf='" + uf + "', " +
                "emp_fone='" + telefone + "', " +
                "emp_email='" + email + "', " +
                "emp_vlremb=" + valorDaEmbalagem +
                "]";
    }
}
