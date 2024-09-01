package esame.unicam.cs.mp.vectorgame.app;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.RaceEngine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

/**
 * The RaceGame class extends GameController and provides the logic for the race game simulation.
 * It handles initializing the game, simulating race turns, resetting the game, and handling user inputs.
 */
public class RaceGame extends GameController {
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button moveUpButton;
    @FXML
    private Button moveDownButton;
    @FXML
    private Button moveLeftButton;
    @FXML
    private Button moveRightButton;
    @FXML
    private Button enableArrowKeysButton;
    @FXML
    private GridPane trackPane;

    private RaceEngine<CircuitField> gameEngine;
    private TrackControllerApp trackView;

    /**
     * Simulates a turn in the race game. Players move randomly, and the game checks if all cars
     * have either finished or crashed. If the race is over, the start button is disabled.
     */
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

    /**
     * Initializes the race game by loading the track, setting player start positions, and preparing the game engine.
     * This method is called upon application start or game reset.
     *
     * @throws IOException if an error occurs during track loading
     */
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

    /**
     * Initializes the players' positions on the start positions of the track. Each player is placed
     * on a designated start cell, preparing the game for a new race or reset.
     *
     * @param players the list of players to position
     * @param track the track containing the start positions
     */
    private void initializePlayersOnStartPositions(List<Player<CircuitField>> players, Track<CircuitField> track) {
        List<CircuitField> startPositions = track.getStartGridPosition();  // Get all start positions

        for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
            Player<CircuitField> player = players.get(i);
            CircuitField startPosition = startPositions.get(i);
            player.setPosition(startPosition);  // Set each player's position to a start position
        }
    }

    /**
     * Checks if all cars have either finished the race or crashed. If all cars are finished or crashed,
     * the game is considered over.
     *
     * @return true if all cars have finished or crashed, false otherwise
     */
    private boolean allCarsFinishedOrCrashed() {
        for (Player<CircuitField> player : gameEngine.getPlayers()) {
            if (!player.hasFinished() && !player.hasCrashed()) {
                return false; // There's still a player who can continue racing
            }
        }
        return true; // All players have either finished or crashed
    }

    /**
     * Resets the game to its initial state. Players are repositioned at the start positions,
     * the game engine is reinitialized, and the track view is updated. The start button is also re-enabled.
     */
    @FXML
    private void resetGame() {
        System.out.println("Resetting the game...");
        try {
            // Get the current track configuration
            Track<CircuitField> track = getTrack();
            List<CircuitField> startPositions = track.getStartGridPosition();  // Get all start positions
            List<Player<CircuitField>> players = getPlayers();  // Get the list of players

            // Reset each player back to a starting position
            for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
                players.get(i).setPosition(startPositions.get(i));  // Reset players to start positions
                players.get(i).setFinished(false); // Reset finished state
            }

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


    // Methods for handling WASD button movements
    @FXML
    private void moveUp() {
        handleWASDKeys("W");
    }

    @FXML
    private void moveDown() {
        handleWASDKeys("S");
    }

    @FXML
    private void moveLeft() {
        handleWASDKeys("A");
    }

    @FXML
    private void moveRight() {
        handleWASDKeys("D");
    }

    /**
     * Handles the WASD key movements for controlling the car.
     *
     * @param direction the direction key pressed, represented as a String ("W", "A", "S", "D").
     */
    private void handleWASDKeys(String direction) {
        Player<CircuitField> player = gameEngine.getPlayers().get(0);

        switch (direction) {
            case "W":
                player.adjustVelocity(0, -1); // Move up
                break;
            case "A":
                player.adjustVelocity(-1, 0); // Move left
                break;
            case "S":
                player.adjustVelocity(0, 1); // Move down
                break;
            case "D":
                player.adjustVelocity(1, 0); // Move right
                break;
        }

        player.updatePosition();
        this.trackView.update();
    }

    /**
     * Handles the WASD key press events to control the car.
     *
     * @param event the KeyEvent representing the key press
     */
    private void handleWASDKeys(KeyEvent event) {
        String direction;
        switch (event.getCode()) {
            case W -> direction = "W";
            case A -> direction = "A";
            case S -> direction = "S";
            case D -> direction = "D";
            default -> {
                return;
            }
        }
        handleWASDKeys(direction);
    }
}
