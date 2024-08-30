package esame.unicam.cs.mp.vectorgame.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VectorGame.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();
        controller.initialize();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vector Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
