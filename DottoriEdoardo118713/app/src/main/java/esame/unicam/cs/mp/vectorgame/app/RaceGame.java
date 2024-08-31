package esame.unicam.cs.mp.vectorgame.app;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.RaceEngine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class RaceGame extends GameController {
    public Label player1Label;
    public Label player2Label;
    public Button nextTurnButton;
    public Button enableArrowKeysButton;
    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;  // Reset button added to the FXML

    @FXML
    private GridPane trackPane;

    private RaceEngine<CircuitField> gameEngine;
    private TrackControllerApp trackView;

    @FXML
    private void simulate() {
        this.trackView.movePlayersRandomly();
        this.trackView.update();

        if (allCarsFinishedOrCrashed()) {
            startButton.setDisable(true);
            System.out.println("Race is over!");
        }
    }

    public void initialize() throws IOException {
        super.initialize();
        Track<CircuitField> track = getTrack();
        List<Player<CircuitField>> players = getPlayers();
        this.trackView = new TrackControllerApp(track, players);
        this.trackPane.add(trackView, 0, 0);

        this.gameEngine = (RaceEngine<CircuitField>) getGameEngine();
        this.gameEngine.setTrack(track);
        this.gameEngine.setPlayers(players);

        initializePlayersOnStartPositions(players, track);
        this.trackView.update();
    }

    private void initializePlayersOnStartPositions(List<Player<CircuitField>> players, Track<CircuitField> track) {
        List<CircuitField> startPositions = track.getStartGridPosition();

        for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
            Player<CircuitField> player = players.get(i);
            CircuitField startPosition = startPositions.get(i);
            player.setPosition(startPosition);
        }
    }

    private boolean allCarsFinishedOrCrashed() {
        for (Player<CircuitField> player : gameEngine.getPlayers()) {
            if (!player.hasFinished() && !player.hasCrashed()) {
                return false;
            }
        }
        return true;
    }


    @FXML
    private void enableArrowKeys() {
        // Make sure the trackView is focused to receive key events
        trackView.requestFocus();
        // Set a handler for key presses, ensuring they control the player movement
        trackView.setOnKeyPressed(trackView.getOnKeyPressed());
        System.out.println("Arrow keys enabled. You can now control the car with the arrow keys.");
    }

    @FXML
    private void resetGame() {
        System.out.println("Resetting the game...");
        try {
            Track<CircuitField> track = getTrack();
            List<CircuitField> startPositions = track.getStartGridPosition();
            List<Player<CircuitField>> players = getPlayers();

            for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
                players.get(i).setPosition(startPositions.get(i));
                players.get(i).reset();
            }

            this.gameEngine.setTrack(track);
            this.gameEngine.setPlayers(players);

            this.trackView.update();
            startButton.setDisable(false);
            System.out.println("Game has been reset.");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to reset the game");
            alert.setContentText("An error occurred while resetting the game.");
            alert.showAndWait();
        }
    }
}
