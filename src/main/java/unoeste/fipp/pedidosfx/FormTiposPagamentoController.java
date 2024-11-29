package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class FormTiposPagamentoController implements Initializable {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            tfNome.requestFocus();
        });
        if(TabelaTiposPagamentoController.tipoPagamento != null){
            // ao editar ja traz os campos preenchidos
            TipoPagamento tipoPagamento = TabelaTiposPagamentoController.tipoPagamento;
            tfId.setText(tipoPagamento.getId()+"");
            tfNome.setText(tipoPagamento.getNome());
        }
    }


    public void onCancelar(javafx.event.ActionEvent actionEvent) {
        tfNome.getScene().getWindow().hide();
    }

    public void onConfirmar(javafx.event.ActionEvent actionEvent) {
        TipoPagamento tipoPagamento = new TipoPagamento(tfNome.getText());
        TipoPagamentoDAL dal = new TipoPagamentoDAL();
        if(TabelaTiposPagamentoController.tipoPagamento==null){
            if(!dal.gravar(tipoPagamento)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }else{
            tipoPagamento.setId(Integer.parseInt(tfId.getText()));
            if(!dal.alterar(tipoPagamento)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao editar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }

        tfNome.getScene().getWindow().hide();
    }
}

