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
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaTiposPagamentoController implements Initializable {
    public static TipoPagamento tipoPagamento = null;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableColumn<TipoPagamento, String> coNome;

    @FXML
    private TableView<TipoPagamento> tableView;

    public void onPesquisar(KeyEvent keyEvent) {
        carregarTabela(tfPesquisar.getText());
    }

    public void onNovoTipoPag(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-tiposPagamento-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 250);
        stage.setTitle("Nova Tipo de Pagamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tfPesquisar.setText("");
        carregarTabela("");
    }

    public void onEditar(ActionEvent actionEvent) throws Exception {
        if(tableView.getSelectionModel().getSelectedIndex() > -1) {
            tipoPagamento = tableView.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-tiposPagamento-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 250);
            stage.setTitle("Alterar Tipo de Pagamento");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tfPesquisar.setText("");
            carregarTabela("");
            tipoPagamento = null;
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tableView.getSelectionModel().getSelectedIndex() > -1){
            TipoPagamento tipoPagamento = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão da categoria?");
            if(alert.showAndWait().get() == ButtonType.OK){
                TipoPagamentoDAL dal = new TipoPagamentoDAL();
                dal.apagar(tipoPagamento);
            }
            carregarTabela("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        if(!filtro.isEmpty()){
            filtro = "upper(tpg_nome) LIKE '%"+filtro.toUpperCase()+"%'";
        }
        TipoPagamentoDAL dal = new TipoPagamentoDAL();
        List<TipoPagamento> tipoPagamentoList = dal.get(filtro);
        tableView.setItems(FXCollections.observableArrayList(tipoPagamentoList));
    }
}
