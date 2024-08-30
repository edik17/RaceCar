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

        // Check if all cars have either finished or crashed
        if (allCarsFinishedOrCrashed()) {
            startButton.setDisable(true);
            System.out.println("Race is over!");
        }
    }

    public void initialize() throws IOException {
        super.initialize();

        // Parse and load the track
        Track<CircuitField> track = getTrack();

        // Initialize the TrackControllerApp with the parsed track and players
        List<Player<CircuitField>> players = getPlayers();
        this.trackView = new TrackControllerApp(track, players);
        this.trackPane.add(trackView, 0, 0);

        // Set the game engine's track and players
        this.gameEngine = (RaceEngine<CircuitField>) getGameEngine();
        this.gameEngine.setTrack(track);
        this.gameEngine.setPlayers(players);

        // Ensure players are positioned on the start cells
        initializePlayersOnStartPositions(players, track);

        // Render the initial state of the track and players
        this.trackView.update();
    }

    private void initializePlayersOnStartPositions(List<Player<CircuitField>> players, Track<CircuitField> track) {
        List<CircuitField> startPositions = track.getStartGridPosition();  // Get all start positions

        for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
            Player<CircuitField> player = players.get(i);
            CircuitField startPosition = startPositions.get(i);
            player.setPosition(startPosition);  // Set each player's position to a start position
        }
    }

    private boolean allCarsFinishedOrCrashed() {
        for (Player<CircuitField> player : gameEngine.getPlayers()) {
            if (!player.hasFinished() && !player.hasCrashed()) {
                return false; // There's still a player who can continue racing
            }
        }
        return true; // All players have either finished or crashed
    }

    public void showRaceWonPopup(Player<CircuitField> player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player " + player.getName() + " has won the race!", ButtonType.OK);
        alert.setTitle("Race Won");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    @FXML
    private void enableArrowKeys() {
        trackView.requestFocus();  // Ensure the pane is focused to receive key events
        trackView.setOnKeyPressed(trackView.getOnKeyPressed());  // Re-apply the key event handler
    }

    @FXML
    private void resetGame() {
        System.out.println("Resetting the game...");
        try {
            // Reset player positions to start positions
            Track<CircuitField> track = getTrack();  // Get the current track configuration
            List<Player<CircuitField>> players = getPlayers();  // Get the list of players
            initializePlayersOnStartPositions(players, track);  // Set players to start positions

            // Reinitialize game engine's state
            this.gameEngine.setTrack(track);
            this.gameEngine.setPlayers(players);

            // Update the track view
            this.trackView.update();
            startButton.setDisable(false);  // Re-enable the start button

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
