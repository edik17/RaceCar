package esame.unicam.cs.mp.vectorgame.api.model.game;

import esame.unicam.cs.mp.vectorgame.api.model.Movement;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

/**
 * Represents a player in the Racetrack game. Each player has a name, position, and velocity.
 * The player can move based on their velocity and can adjust their velocity each turn.
 *
 * @param <T> the specific type of grid used in the game
 */
public abstract class Player<T extends Grid<T>> implements Gamer<T> {
    private T currentPosition;
    private Movement<T> lastMove;
    private final Track<T> track;
    private boolean finished = false;

    private int velocityX = 0; // Initial horizontal velocity
    private int velocityY = 0; // Initial vertical velocity

    public Player(T startPosition, Track<T> track) {
        this.currentPosition = startPosition;
        this.track = track;
        this.lastMove = null;
    }

    // Existing methods...

    /**
     * Updates the player's position based on the current velocity.
     */
    public void updatePosition() {
        if (this.currentPosition == null) {
            System.out.println("Player has no valid position. Skipping update.");
            return; // Skip updating if the player has crashed or is out of track
        }

        int newX = currentPosition.getX() + velocityX;
        int newY = currentPosition.getY() + velocityY;
        T newPosition = track.getField(newX, newY);

        if (newPosition != null && !track.isOutOfTrack(newPosition)) {
            this.lastMove = new Movement<>(this.currentPosition, newPosition);
            this.currentPosition = newPosition;
        } else {
            this.currentPosition = null; // Out of bounds or crashed
        }
    }

    /**
     * Adjusts the player's velocity. This method is called to change the player's speed.
     *
     * @param deltaX change in the x component of velocity
     * @param deltaY change in the y component of velocity
     */
    public void adjustVelocity(int deltaX, int deltaY) {
        this.velocityX += deltaX;
        this.velocityY += deltaY;
    }

    // Methods to check if player has finished or crashed
    public boolean hasFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean hasCrashed() {
        return this.currentPosition == null || this.currentPosition.getCarType() == RaceCar.OUT_OF_TRACK;
    }

    public void reset() {
        this.currentPosition = track.getStartGridPosition().get(0);
        this.finished = false;
        this.velocityX = 0;
        this.velocityY = 0;
    }
}
