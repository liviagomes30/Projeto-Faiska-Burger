package unoeste.fipp.pedidosfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public void onCadEmpresa(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-empresa-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 800);
        stage.setTitle("Cadastro de Empresa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onCadProduto(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-produtos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Cadastro de Produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onCadCategoria(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-categorias-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Cadastro de Categorias");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void onSair(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente sair?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void onNovoPedido(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("form-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de Produtos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onAbrirPedido(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-pedidos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Consulta de pedidos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onRelProduto(ActionEvent actionEvent) {
    }

    public void onRelPedidosPeriodo(ActionEvent actionEvent) {
    }

    public void onRelCardapio(ActionEvent actionEvent) {
    }

    public void onSobre(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Faiska Burguer System");
        alert.setContentText("Desenvolvido por FIPP Sistemas\n2024");
        alert.show();
    }

    public void onHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Ajuda - Faiska Burguer System");
        alert.setContentText("Para suporte técnico, entre em contato:\n\n" +
                "Email: suporte@faiskaburguer.com\n" +
                "Telefone: (11) 99999-9999\n\n" +
                "Horário de atendimento: Segunda a Sexta, das 9h às 18h.");
        alert.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onNovoTipoPag(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(PedidosFX.class.getResource("tabela-tiposPagamento-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Cadastro de Tipos de Pagamento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}