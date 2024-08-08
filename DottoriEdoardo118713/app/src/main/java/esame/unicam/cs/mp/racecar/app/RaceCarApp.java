package esame.unicam.cs.mp.racecar.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class RaceCarApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carica il file FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RaceCarApp.fxml")));

        // Crea la scena passando la root
        Scene scene = new Scene(root);

        // Imposta il titolo della finestra
        primaryStage.setTitle("Race Car App");

        // Imposta la scena al primaryStage
        primaryStage.setScene(scene);

        // Blocca la possibilità di ridimensionare la finestra
        primaryStage.setResizable(false);

        // Mostra la finestra
        primaryStage.show();
    }
}
