package Client;

import Client.controller.EmpController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmpApplication.class.getResource("emp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        EmpController controller = fxmlLoader.getController();
        stage.setTitle("Le Maraicher en Ligne");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnHiding(e-> {
            controller.shutdown();
            Platform.exit();
        });
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}
