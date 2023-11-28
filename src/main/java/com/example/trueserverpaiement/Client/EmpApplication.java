package com.example.trueserverpaiement.Client;

import com.example.trueserverpaiement.LaunchEmp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EmpApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("[START] start");
        FXMLLoader fxmlLoader = new FXMLLoader(LaunchEmp.class.getResource("/com/example/trueserverpaiement/emp-view.fxml"));
        System.out.println("[START] FXMLLoader()");
        Scene scene = new Scene(fxmlLoader.load(), 752, 511);
        System.out.println("[START] Scene(fxmlLoader.load()");
        stage.setTitle("Le Maraicher en Ligne");
        stage.setResizable(false);
        stage.setScene(scene);
        EmpController empController = fxmlLoader.getController();
        stage.setOnHiding(e -> {
            empController.shutdown();
            Platform.exit();
        });
        stage.show();
        System.out.println("[START] show()");
    }

    public static void main(String[] args) {
        launch();
    }
}