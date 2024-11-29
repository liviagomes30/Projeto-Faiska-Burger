package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import java.net.URL;
import java.util.ResourceBundle;

public class FormCategoriasController implements Initializable {
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    public void onConfirmar(ActionEvent actionEvent) {
        Categoria categoria = new Categoria(tfNome.getText());
        CategoriaDAL dal = new CategoriaDAL();
        if(TabelaCategoriasController.categoria==null){
            if(!dal.gravar(categoria)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }else{
            categoria.setId(Integer.parseInt(tfId.getText()));
            if(!dal.alterar(categoria)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao editar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }

        tfNome.getScene().getWindow().hide();
    }

    public void onCancelar(ActionEvent actionEvent) {
        tfNome.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            tfNome.requestFocus();
        });
        if(TabelaCategoriasController.categoria != null){
            // ao editar ja traz os campos preenchidos
            Categoria categoria = TabelaCategoriasController.categoria;
            tfId.setText(categoria.getId()+"");
            tfNome.setText(categoria.getNome());

        }
    }
}
