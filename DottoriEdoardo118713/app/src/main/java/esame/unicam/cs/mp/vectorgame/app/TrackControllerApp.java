package esame.unicam.cs.mp.vectorgame.app;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TrackControllerApp extends Pane {

    private static final int CELL_SIZE = 30;  // The size of each cell in pixels
    private final Track<CircuitField> track;
    private final List<Player<CircuitField>> players;
    private final Canvas canvas;
    private final Map<Player<CircuitField>, Color> playerColors = new HashMap<>();  // Map to store player colors
    private final Random random;

    public TrackControllerApp(Track<CircuitField> track, List<Player<CircuitField>> players) {
        this.track = track;
        this.players = players;
        this.canvas = new Canvas(track.getHeight() * CELL_SIZE, track.getWidth() * CELL_SIZE);
        this.getChildren().add(canvas);
        this.random = new Random();
        assignColorsToPlayers();
        setStartPositions();  // Ensure players start on the green line
        drawTrack();
        setFocusTraversable(true);
        requestFocus();  // Ensure the pane is focused to receive key events
        addKeyEventHandlers();
    }

    private void assignColorsToPlayers() {
        Color[] colors = {Color.BLUE, Color.ORCHID, Color.YELLOW, Color.CYAN};
        int colorIndex = 0;
        for (Player<CircuitField> player : players) {
            playerColors.put(player, colors[colorIndex % colors.length]);
            colorIndex++;
        }
    }

    // New method to set player positions to start (green) positions
    private void setStartPositions() {
        List<CircuitField> startPositions = track.getStartGridPosition();
        for (int i = 0; i < players.size() && i < startPositions.size(); i++) {
            players.get(i).setPosition(startPositions.get(i));
        }
    }

    private void drawTrack() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        CircuitField[][] cells = track.getGridAsMatrix();

        int trackWidth = track.getWidth();
        int trackHeight = track.getHeight();

        for (int y = 0; y < trackHeight; y++) {
            for (int x = 0; x < trackWidth; x++) {
                // Ensure x and y are within bounds before accessing the array
                if (x < cells[0].length && y < cells.length) {
                    CircuitField cell = cells[y][x];
                    if (cell != null) {
                        switch (cell.getCarType()) {
                            case TRACK:
                                gc.setFill(Color.GRAY);
                                break;
                            case START:
                                gc.setFill(Color.GREEN);
                                break;
                            case FINISH:
                                gc.setFill(Color.RED);
                                break;
                            case OUT_OF_TRACK:
                                gc.setFill(Color.BLACK);
                                break;
                        }
                        gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
        drawPlayers();  // Draw players after drawing the track
    }

    public void update() {
        drawTrack();
        drawPlayers();
    }

    private void drawPlayers() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Player<CircuitField> player : players) {
            CircuitField pos = player.getCurrentPosition();
            if (pos != null) {
                gc.setFill(playerColors.get(player));
                gc.fillOval(pos.getX() * CELL_SIZE, pos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void addKeyEventHandlers() {
        this.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        for (Player<CircuitField> player : players) {
            CircuitField pos = player.getCurrentPosition();
            if (pos != null) {
                int newX = pos.getX();
                int newY = pos.getY();
                switch (event.getCode()) {
                    case UP:
                        newY -= 1; // Move up
                        break;
                    case LEFT:
                        newX -= 1; // Move left
                        break;
                    case DOWN:
                        newY += 1; // Move down
                        break;
                    case RIGHT:
                        newX += 1; // Move right
                        break;
                    default:
                        break;
                }
                CircuitField newPosition = track.getField(newX, newY);
                if (newPosition != null && !track.isOutOfTrack(newPosition)) {
                    player.setPosition(newPosition);
                    if (newPosition.getCarType() == RaceCar.FINISH) {
                        player.isFinished(); // Mark player as finished
                        showRaceWonPopup(player);  // Show the popup when the car crosses the finish line
                        return; // Stop further processing as the race is won
                    }
                }
            }
        }
        update();
    }
    private void showRaceWonPopup(Player<CircuitField> player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player " + player.getName() + " has won the race!", ButtonType.OK);
        alert.setTitle("Race Won");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    public void movePlayersRandomly() {
        for (Player<CircuitField> player : players) {
            if (!player.hasFinished() && !player.hasCrashed()) {
                // Move player randomly (or use a more sophisticated strategy)
                CircuitField newPosition = player.adjacentMove();  // Assuming `adjacentMove` gives a random valid move
                player.setPosition(newPosition);

                // Check if the player is out of the track after the move
                if (track.isOutOfTrack(newPosition)) {
                    //player.setCrashed(true); // Mark player as crashed
                    player.setPosition(null); // Remove from the track
                } else if (newPosition.getCarType() == RaceCar.FINISH) {
                    player.isFinished(); // Mark player as finished
                    showRaceWonPopup(player);  // Show the popup when the car crosses the finish line
                    return; // Stop further processing as the race is won
                }
            }
        }
        update();
    }



}
