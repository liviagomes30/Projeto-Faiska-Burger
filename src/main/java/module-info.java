module unoeste.fipp.pedidosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itext;

    opens unoeste.fipp.pedidosfx to javafx.fxml;
    opens unoeste.fipp.pedidosfx.db.entidade to javafx.fxml;
    exports unoeste.fipp.pedidosfx;
    exports unoeste.fipp.pedidosfx.db.entidade;
}