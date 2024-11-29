package unoeste.fipp.pedidosfx;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import unoeste.fipp.pedidosfx.db.dal.PedidoDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.dal.TipoPagamentoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.util.MaskFieldUtil;
import unoeste.fipp.pedidosfx.util.ModalTable;

import java.awt.*;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class FormPedidosController implements Initializable {

    @FXML
    private Button btProduto;

    @FXML
    private ComboBox<TipoPagamento> cbTipoPagamento;

    @FXML
    private TableColumn<Pedido.Item, String> coProduto;

    @FXML
    private TableColumn<Pedido.Item, String> coQuantidade;

    @FXML
    private TableColumn<Pedido.Item, String> coValor;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lbTotal;

    @FXML
    private CheckBox rbViagem;

    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private TableView<Pedido.Item> tableView;

    @FXML
    private TextField tfCliente;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfTelefone;

    private Produto produto = null;

    @FXML
    void onAdicionar(ActionEvent event) {
        if (produto != null) {
            boolean encontrou = false;
            for (Pedido.Item item : tableView.getItems()) {
                if (item.produto().getId() == produto.getId()) {
                    int newQuantity = item.quant() + spQuantidade.getValue();
                    Pedido.Item updatedItem = new Pedido.Item(produto, newQuantity, produto.getValor());
                    int index = tableView.getItems().indexOf(item);
                    tableView.getItems().set(index, updatedItem);
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                Pedido.Item item = new Pedido.Item(produto, spQuantidade.getValue(), produto.getValor());
                tableView.getItems().add(item);
            }

            btProduto.setText("Selecione o item");
            spQuantidade.getValueFactory().setValue(1);
            atualizarTotal();
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        atualizarTotal();
        Pedido pedido;
        
        if (TabelaPedidosController.pedido != null) {
            pedido = TabelaPedidosController.pedido;
        } else {
            pedido = new Pedido();
        }

        pedido.setData(dpData.getValue());
        pedido.setFoneCliente(tfTelefone.getText());
        pedido.setNomeCliente(tfCliente.getText());
        pedido.setTipoPagamento(cbTipoPagamento.getValue());
        pedido.setViagem(rbViagem.isSelected() ? 'S' : 'N');
        pedido.getItens().clear(); // Limpa itens existentes
        for (Pedido.Item item : tableView.getItems()) {

            pedido.addItem(item);
        }

        pedido.setTotal(
                Double.parseDouble(lbTotal.getText().replace("R$ ", "").replace(
                    ",", "."))); // Ajusta o total

        PedidoDAL pedidoDAL = new PedidoDAL();

        if (pedido.getId() == 0) {
            pedidoDAL.gravar(pedido);
        } else {
            pedidoDAL.alterar(pedido);
        }

        btProduto.getScene().getWindow().hide();
    }

    @FXML
    void onSelProduto(ActionEvent event) {
        ModalTable mt = new ModalTable(
                new ProdutoDAL().get(""),
                new String[] { "id", "nome", "valor", "categoria" }, "nome");
        Stage stage = new Stage();
        stage.setScene(new Scene(mt));
        stage.setWidth(600);
        stage.setHeight(480);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        produto = (Produto) mt.getSelecionado();
        if (produto != null) {
            btProduto.setText(produto.getNome());
        }
    }


    private void carregarTiposPagamento() {
        List<TipoPagamento> tipoPagamentoList = new TipoPagamentoDAL().get("");
        cbTipoPagamento.setItems(
                FXCollections.observableArrayList(tipoPagamentoList));
        cbTipoPagamento.getSelectionModel().select(0);
    }

    private void atualizarTotal() {
        double total = 0.0;
        for (Pedido.Item item : tableView.getItems()) {
            total += item.quant() * item.valor();
        }
        lbTotal.setText(String.format("R$ %.2f", total));
    }

    public void onPrint(ActionEvent actionEvent) {
        try {
            // Configurar o documento
            Document document = new Document(PageSize.A4);

            // Obter o caminho do Desktop
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
            File desktopDir = new File(desktopPath);
            if (!desktopDir.exists()) {
                desktopDir.mkdirs(); // Cria o diretório, se necessário
            }

            // Criar o nome e caminho completo do arquivo
            String fileName = "pedido_" + System.currentTimeMillis() + ".pdf";
            String fullPath = desktopPath + File.separator + fileName;

            // Depuração do caminho
            System.out.println("Caminho do PDF: " + fullPath);

            // Configurar o PdfWriter
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullPath));

            // Adicionar o cabeçalho e rodapé com o evento de página
            writer.setPageEvent(new PdfPageEventHelper() {
                public void onEndPage(PdfWriter writer, Document document) {
                    try {
                        // Rodapé
                        Font footerFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
                        Paragraph footer = new Paragraph("Página " + writer.getPageNumber(), footerFont);
                        footer.setAlignment(Element.ALIGN_CENTER);
                        footer.setSpacingBefore(10);
                        document.add(footer);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Abrir o documento
            document.open();

            // Adicionar o cabeçalho
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Paragraph header = new Paragraph("Pedido de Venda", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(10);
            document.add(header);

            // Adicionar as informações do cliente e do pedido
            Font bodyFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Paragraph clientInfo = new Paragraph("Informações do Pedido:", bodyFont);
            clientInfo.setSpacingAfter(5);
            document.add(clientInfo);

            Paragraph customerDetails = new Paragraph();
            customerDetails.add("Cliente: " + tfCliente.getText() + "\n");
            customerDetails.add("Telefone: " + tfTelefone.getText() + "\n");
            customerDetails.add("Data: " + dpData.getValue() + "\n");
            customerDetails.add("Tipo de Pagamento: " + cbTipoPagamento.getValue().getNome() + "\n");
            customerDetails.add("Viagem: " + (rbViagem.isSelected() ? "Sim" : "Não"));
            document.add(customerDetails);

            // Adicionar um espaço entre as informações e os itens
            document.add(new Paragraph("\n"));

            // Título da tabela de itens
            Paragraph itemsTitle = new Paragraph("Itens do Pedido:", bodyFont);
            itemsTitle.setSpacingBefore(10);
            document.add(itemsTitle);

            // Configurar a tabela
            PdfPTable table = new PdfPTable(3); // 3 colunas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // Cabeçalhos da tabela
            PdfPCell cell = new PdfPCell(new Phrase("Produto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantidade", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Preço Unitário", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Adicionar os itens do pedido
            for (Pedido.Item item : tableView.getItems()) {
                table.addCell(item.produto().getNome());
                table.addCell(String.valueOf(item.quant()));
                table.addCell("R$ " + item.valor());
            }

            document.add(table);

            // Adicionar o total
            Paragraph total = new Paragraph("\nTotal: R$ " + lbTotal.getText().replace("R$ ", ""), bodyFont);
            total.setSpacingBefore(10);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            // Fechar o documento
            document.close();

            // Abrir o arquivo PDF gerado
            Desktop.getDesktop().open(new File(fullPath));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coProduto.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().produto().getNome()));
        coQuantidade.setCellValueFactory(
                cellData -> new SimpleStringProperty("" + cellData.getValue().quant()));
        coValor.setCellValueFactory(
                cellData -> new SimpleStringProperty("" + cellData.getValue().valor()));

        MaskFieldUtil.foneField(tfTelefone);
        spQuantidade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        carregarTiposPagamento();

        if (TabelaPedidosController.pedido != null) {
            Pedido pedido = TabelaPedidosController.pedido;
            tfID.setText(String.valueOf(pedido.getId()));
            dpData.setValue(pedido.getData());
            tfCliente.setText(pedido.getNomeCliente());
            tfTelefone.setText(pedido.getFoneCliente());
            cbTipoPagamento.setValue(pedido.getTipoPagamento());
            rbViagem.setSelected(pedido.getViagem() == 'S');

            tableView.setItems(FXCollections.observableArrayList(pedido.getItens()));
            System.out.println(pedido.getItens().toString());

            atualizarTotal();
        }

        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Editar Quantidade");
        editItem.setOnAction(event -> {
            Pedido.Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedItem.quant()));
                dialog.setTitle("Editar Quantidade");
                dialog.setHeaderText(null);
                dialog.setContentText("Nova quantidade:");

                dialog.showAndWait().ifPresent(newQuantity -> {
                    try {
                        int quant = Integer.parseInt(newQuantity);
                        if (quant > 0) {
                            Pedido.Item updatedItem = new Pedido.Item(selectedItem.produto(), quant, selectedItem.valor());
                            int index = tableView.getItems().indexOf(selectedItem);
                            tableView.getItems().set(index, updatedItem);
                            atualizarTotal();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantidade deve ser maior que 0.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Quantidade inválida.", ButtonType.OK);
                        alert.showAndWait();
                    }
                });
            }
        });

        MenuItem deleteItem = new MenuItem("Remover");
        deleteItem.setOnAction(event -> {
            Pedido.Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                tableView.getItems().remove(selectedItem);
                atualizarTotal();
            }
        });

        contextMenu.getItems().addAll(editItem, deleteItem);

        // Associa o menu de contexto à tabela
        tableView.setRowFactory(tv -> {
            TableRow<Pedido.Item> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    tableView.getSelectionModel().select(row.getItem());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });

    }
}
