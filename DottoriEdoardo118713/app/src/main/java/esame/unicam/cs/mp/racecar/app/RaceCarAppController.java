package esame.unicam.cs.mp.racecar.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class RaceCarAppController{

    @FXML
    private Label speedLabel;

    @FXML
    private ImageView carImageView;

    private int speed = 0;

    @FXML
    private void handleAccelerate() {
        speed += 10;
        updateSpeed();
    }

    @FXML
    private void handleBrake() {
        speed = Math.max(0, speed - 10);
        updateSpeed();
    }

    @FXML
    private void handleTurnLeft() {
        carImageView.setLayoutX(carImageView.getLayoutX() - 10);
    }

    @FXML
    private void handleTurnRight() {
        carImageView.setLayoutX(carImageView.getLayoutX() + 10);
    }

    @FXML
    private void handlePause() {
        // Logica per mettere in pausa il gioco
    }

    @FXML
    private void handleReset() {
        speed = 0;
        updateSpeed();
        carImageView.setLayoutX(275);
        carImageView.setLayoutY(175);
    }

    private void updateSpeed() {
        speedLabel.setText(speed + " km/h");
    }
}
