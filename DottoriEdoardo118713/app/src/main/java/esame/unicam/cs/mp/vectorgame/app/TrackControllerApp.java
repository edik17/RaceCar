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

public class TrackControllerApp extends Pane {
    private static final int CELL_SIZE = 30;
    private final Track<CircuitField> track;
    private final List<Player<CircuitField>> players;
    private final Canvas canvas;
    private final Map<Player<CircuitField>, Color> playerColors = new HashMap<>();

    public TrackControllerApp(Track<CircuitField> track, List<Player<CircuitField>> players) {
        this.track = track;
        this.players = players;
        this.canvas = new Canvas(track.getWidth() * CELL_SIZE, track.getHeight() * CELL_SIZE);
        this.getChildren().add(canvas);
        assignColorsToPlayers();
        setStartPositions();
        drawTrack();
        setFocusTraversable(true);
        requestFocus();
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
                CircuitField cell = cells[x][y];
                if (cell != null) {
                    switch (cell.getCarType()) {
                        case TRACK:
                            gc.setFill(Color.DARKGRAY);
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
        drawPlayers();
    }

    private void addKeyEventHandlers() {
        this.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        for (Player<CircuitField> player : players) {
            if (player.getCurrentPosition() == null) {
                continue;
            }

            switch (event.getCode()) {
                case W:
                    player.adjustVelocity(0, -1); // Move up
                    break;
                case S:
                    player.adjustVelocity(0, 1);  // Move down
                    break;
                case A:
                    player.adjustVelocity(-1, 0); // Move left
                    break;
                case D:
                    player.adjustVelocity(1, 0);  // Move right
                    break;
                default:
                    return; // Ignore other keys
            }

            player.updatePosition();

            if (player.hasFinished()) {
                showRaceWonPopup(player);
                return;
            } else if (player.hasCrashed()) {
                System.out.println("Player has crashed!");
            }
        }
        update();
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
                gc.setFill(playerColors.get(player)); // Set color based on the player
                double cornerRadius = 10; // Adjust the radius as needed
                gc.fillRoundRect(
                        pos.getX() * CELL_SIZE,
                        pos.getY() * CELL_SIZE,
                        CELL_SIZE,
                        CELL_SIZE,
                        cornerRadius, // x-axis radius of the arc at the four corners
                        cornerRadius  // y-axis radius of the arc at the four corners
                );
            }
        }
    }

    private void showRaceWonPopup(Player<CircuitField> player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player  has won the race!", ButtonType.OK);
        alert.setTitle("Race Won");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void movePlayersRandomly() {
        for (Player<CircuitField> player : players) {
            if (!player.hasFinished() && !player.hasCrashed()) {
                CircuitField newPosition = (CircuitField) player.adjacentMove();
                if (newPosition == null || newPosition.getCarType() == RaceCar.OUT_OF_TRACK) {
                    player.setPosition(null);
                    System.out.println("Player has crashed!");
                } else {
                    player.setPosition(newPosition);
                    System.out.println("Player moved to: (" + newPosition.getX() + ", " + newPosition.getY() + ")");

                    if (newPosition.getCarType() == RaceCar.FINISH) {
                        player.hasFinished();
                        showRaceWonPopup(player);
                        break;
                    }
                }
            }
        }
        update();
    }
}
