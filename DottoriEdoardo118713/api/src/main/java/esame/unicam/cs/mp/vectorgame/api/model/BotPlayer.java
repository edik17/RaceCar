package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.IMovement;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.List;
import java.util.Random;

/**
 * A class that represents a bot player in the game. The bot makes movements based on a random strategy,
 * attempting to stay on track and reach the finish line.
 */
public class BotPlayer extends Player<CircuitField> {
    private final Random random;
    private CircuitField currentPosition;
    private IMovement<CircuitField> lastMove;
    private final Track<CircuitField> track;

    public BotPlayer( CircuitField startPosition, Track<CircuitField> track) {
        super( startPosition, track);
        this.currentPosition = startPosition;
        this.track = track;
        this.random = new Random();
    }

    @Override
    public CircuitField getCurrentPosition() {
        return currentPosition; // Returns the current position of the bot
    }

    @Override
    public IMovement<CircuitField> getLastMove() {
        return lastMove; // Returns the last move made by the bot
    }

    @Override
    public Track<CircuitField> getTrack() {
        return track; // Returns the track associated with this bot
    }

    @Override
    public void setPosition(CircuitField newPosition) {
        this.lastMove = new Movement<>(this.currentPosition, newPosition); // Stores the last move
        this.currentPosition = newPosition; // Updates the bot's current position
    }

    @Override
    public CircuitField adjacentMove() {
        List<CircuitField> neighbors = getCurrentPosition().getNeighbors();
        List<CircuitField> safeNeighbors = neighbors.stream()
                .filter(n -> n.getCarType().equals(RaceCar.TRACK) || n.getCarType().equals(RaceCar.FINISH))
                .toList();

        // Random chance to simulate a risk of going out of track
        if (safeNeighbors.isEmpty() || random.nextInt(20) == 0) {
            return new CircuitField(-1, -1, RaceCar.OUT_OF_TRACK); // Simulates a crash or out-of-track move
        }

        // Returns a random safe neighbor to move to
        return safeNeighbors.get(random.nextInt(safeNeighbors.size()));
    }

    @Override
    public CircuitField move() {
        if (getLastMove() == null) {
            return adjacentMove(); // If no previous move, pick an adjacent safe move
        }

        int x = getCurrentPosition().getX() + getLastMove().getDeltaX();
        int y = getCurrentPosition().getY() + getLastMove().getDeltaY();

        // Check if the calculated position is within track bounds
        if (x < 0 || y < 0 || x >= getTrack().getWidth() || y >= getTrack().getHeight()) {
            return adjacentMove(); // If out of bounds, pick a safe adjacent move
        } else {
            CircuitField next = getTrack().getCell(x, y);
            if (next != null && next.getCarType().equals(RaceCar.TRACK)) {
                return next; // Move forward if on track
            } else {
                return adjacentMove(); // Otherwise, pick a safe adjacent move
            }
        }
    }
}
