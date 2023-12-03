module com.example.trueserverpaiement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;


    exports com.example.trueserverpaiement.Client;
    opens com.example.trueserverpaiement.Client to javafx.fxml;
    exports com.example.trueserverpaiement;
    opens com.example.trueserverpaiement to javafx.fxml;

    opens com.example.trueserverpaiement.Client.Model to javafx.base;

}