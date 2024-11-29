package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;
import unoeste.fipp.pedidosfx.util.MaskFieldUtil;

public class FormProdutosController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.monetaryField(tfValor);
        Platform.runLater(()->{
            tfNome.requestFocus();
        });
        carregarCategorias();
        if(TabelaProdutosController.produto != null){
            // ao editar ja traz os campos preenchidos
            Produto produto = TabelaProdutosController.produto;
            tfId.setText(produto.getId()+"");
            tfNome.setText(produto.getNome());
            taDesc.setText(produto.getDesc());
            tfValor.setText(produto.getValor()*10+"");
            cbCategoria.getSelectionModel().select(produto.getCategoria());
        }
    }

    private void carregarCategorias() {
        List<Categoria> categoriaList;
        categoriaList = new CategoriaDAL().get("");
        cbCategoria.setItems(FXCollections.observableArrayList(categoriaList));
        cbCategoria.getSelectionModel().select(0); // categoria em pré-selecionada
    }

    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private TextArea taDesc;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfValor;

    @FXML
    void onCancelar(ActionEvent event) {
        taDesc.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        double valor = Double.parseDouble(tfValor.getText().replace(",","."));
        Produto produto = new Produto(tfNome.getText(), taDesc.getText(),valor,cbCategoria.getSelectionModel().getSelectedItem());
        ProdutoDAL dal = new ProdutoDAL();
        if(TabelaProdutosController.produto==null){
            if(!dal.gravar(produto)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }else{
            produto.setId(Integer.parseInt(tfId.getText()));
            if(!dal.alterar(produto)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao editar\n"+ SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }

        taDesc.getScene().getWindow().hide(); // fechando a janela
    }
}
