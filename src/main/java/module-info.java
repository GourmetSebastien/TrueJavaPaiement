module com.example.trueserverpaiement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.trueserverpaiement to javafx.fxml;
    exports Client.controller;
    opens Client.controller to javafx.fxml;
}