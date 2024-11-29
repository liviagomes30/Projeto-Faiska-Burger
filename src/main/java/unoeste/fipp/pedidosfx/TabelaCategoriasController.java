package unoeste.fipp.pedidosfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaCategoriasController implements Initializable {
    public static Categoria categoria = null;
    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableColumn<Categoria, String> coNome;

    @FXML
    private TableView<Categoria> tableView;

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carregarTabela("");
    }


    public void onPesquisar(KeyEvent keyEvent) {
        carregarTabela(tfPesquisar.getText());
    }

    public void onNovaCategoria(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-categorias-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 250);
        stage.setTitle("Nova Categoria");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tfPesquisar.setText("");
        carregarTabela("");
    }

    public void onEditar(ActionEvent actionEvent) throws Exception{
        if(tableView.getSelectionModel().getSelectedIndex() > -1) {
            categoria = tableView.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-categorias-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 250);
            stage.setTitle("Alterar Categoria");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tfPesquisar.setText("");
            carregarTabela("");
            categoria = null;
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tableView.getSelectionModel().getSelectedIndex() > -1){
            Categoria categoria = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão da categoria?");
            if(alert.showAndWait().get() == ButtonType.OK){
                CategoriaDAL dal = new CategoriaDAL();
                dal.apagar(categoria);
            }
            carregarTabela("");
        }
    }

    private void carregarTabela(String filtro) {
        if(!filtro.isEmpty()){
            filtro = "upper(cat_nome) LIKE '%"+filtro.toUpperCase()+"%'";
        }
        CategoriaDAL dal = new CategoriaDAL();
        List<Categoria> categoriaList = dal.get(filtro);
        tableView.setItems(FXCollections.observableArrayList(categoriaList));
    }
}
