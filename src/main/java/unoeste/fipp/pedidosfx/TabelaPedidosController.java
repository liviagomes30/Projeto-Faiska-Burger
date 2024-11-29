package unoeste.fipp.pedidosfx;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unoeste.fipp.pedidosfx.db.dal.PedidoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;

public class TabelaPedidosController implements Initializable {

    @FXML
    private TableColumn<Pedido, String> coCliente;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Pedido> tableView;

    @FXML
    private TableColumn<Pedido, Date> coData;

    @FXML
    private TableColumn<Pedido, Double> coTotal;

    public static Pedido pedido = null;

    public void onPesquisar(KeyEvent keyEvent) {
        carregarTabela(tfPesquisar.getText());
    }

    public void onNovoPedido(javafx.event.ActionEvent actionEvent)
            throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Novo Pedido");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tfPesquisar.setText("");
        carregarTabela("");
    }

    public void onEditar(javafx.event.ActionEvent actionEvent) throws Exception {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            pedido = tableView.getSelectionModel().getSelectedItem();

            System.out.println(pedido);

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("Alterar Pedido");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            tfPesquisar.setText("");
            carregarTabela("");
            pedido = null;
        }
    }

    public void onApagar(javafx.event.ActionEvent actionEvent) {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            Pedido pedido = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirma a exclusão do pedido?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                PedidoDAL dal = new PedidoDAL();
                dal.apagar(pedido);
            }
            carregarTabela("");
        }
    }

    private void carregarTabela(String filtro) {
        if (!filtro.isEmpty()) {
            filtro = "upper(ped_clinome) LIKE '%" + filtro.toUpperCase() + "%'";
        }
        PedidoDAL dal = new PedidoDAL();
        List<Pedido> pedidoList = dal.get(filtro);
        tableView.setItems(FXCollections.observableArrayList(pedidoList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        coData.setCellValueFactory(new PropertyValueFactory<>("data"));
        coTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        carregarTabela("");
    }
}
