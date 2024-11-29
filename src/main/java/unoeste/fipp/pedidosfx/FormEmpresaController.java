package unoeste.fipp.pedidosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import unoeste.fipp.pedidosfx.db.dal.EmpresaDAL;
import unoeste.fipp.pedidosfx.db.entidade.Empresa;
import unoeste.fipp.pedidosfx.util.MaskFieldUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class FormEmpresaController implements Initializable {
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfCnpj;
    @FXML
    private TextField tfRazaoSocial;
    @FXML
    private TextField tfNomeFantasia;
    @FXML
    private TextField tfRua;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfUf;
    @FXML
    private TextField tfBairro;
    @FXML
    private TextField tfCep;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfValorEmbalagem;
    @FXML
    private TextField tfCidade;

    @FXML
    void onCancelar(ActionEvent event) {
        tfRua.getScene().getWindow().hide();
    }

    @FXML
    void onAlterar(ActionEvent event) {
        EmpresaDAL empresaDAL = new EmpresaDAL();
        Empresa empresa = null;

        // Buscar empresa existente
        if (!empresaDAL.get("").isEmpty())
            empresa = empresaDAL.get("").get(0);

        boolean empresaJaExiste = (empresa != null);
        if (empresa == null)
            empresa = new Empresa();

        // Preencher dados da empresa
        empresa.setRazaoSocial(tfRazaoSocial.getText());
        empresa.setBairro(tfBairro.getText());
        empresa.setCep(tfCep.getText());
        empresa.setCnpj(tfCnpj.getText());
        empresa.setCidade(tfCidade.getText());
        empresa.setEmail(tfEmail.getText());
        empresa.setNomeFantasia(tfNomeFantasia.getText());
        empresa.setNumeroDaRua(tfNumero.getText());
        empresa.setRua(tfRua.getText());
        empresa.setTelefone(tfTelefone.getText());
        empresa.setUf(tfUf.getText());

        // Tratamento para valor da embalagem
        try {
            empresa.setValorDaEmbalagem(Double.parseDouble(tfValorEmbalagem.getText()));
        } catch (NumberFormatException e) {
            empresa.setValorDaEmbalagem(0.0);
        }

        // Gravar ou alterar empresa
        try {
            if (empresaJaExiste)
                empresaDAL.alterar(empresa);
            else
                empresaDAL.gravar(empresa);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao salvar os dados da empresa: " + e.getMessage());
            alert.showAndWait();
            return;
        }

        // Fechar janela
        tfRua.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Aplicar máscaras aos campos
        MaskFieldUtil.cnpjField(tfCnpj);
        MaskFieldUtil.cepField(tfCep);
        MaskFieldUtil.foneField(tfNumero);
        MaskFieldUtil.numericField(tfValorEmbalagem);

        EmpresaDAL empresaDAL = new EmpresaDAL();
        Empresa empresa = null;

        // Buscar empresa existente
        if (!empresaDAL.get("").isEmpty())
            empresa = empresaDAL.get("").get(0);

        // Preencher campos se empresa existir
        if (empresa != null) {
            tfRazaoSocial.setText(empresa.getRazaoSocial());
            tfBairro.setText(empresa.getBairro());
            tfCep.setText(empresa.getCep());
            tfCnpj.setText(empresa.getCnpj());
            tfCidade.setText(empresa.getCidade());
            tfEmail.setText(empresa.getEmail());
            tfNomeFantasia.setText(empresa.getNomeFantasia());
            tfNumero.setText(empresa.getNumeroDaRua());
            tfRua.setText(empresa.getRua());
            tfTelefone.setText(empresa.getTelefone());
            tfUf.setText(empresa.getUf());
            tfValorEmbalagem.setText(String.valueOf(empresa.getValorDaEmbalagem()));
        } else {
            // Valor padrão
            tfValorEmbalagem.setText("0");
        }


    }
}
