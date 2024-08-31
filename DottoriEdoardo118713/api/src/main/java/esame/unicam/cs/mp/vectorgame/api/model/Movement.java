package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.IMovement;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

/**
 * The Movement class represents a movement action in the game,
 * encapsulating a starting position and a finishing position on the grid.
 * It provides methods to calculate the movement's direction and validate
 * the movement within a specific track.
 *
 * @param <T> the type of grid cells involved in the movement, extending {@link Grid}.
 */
public class Movement<T extends Grid<T>> implements IMovement<T> {

    private final T start;
    private final T finish;

    /**
     * Constructs a Movement with the specified start and finish positions.
     *
     * @param start  the starting position of the movement
     * @param finish the finishing position of the movement
     */
    public Movement(T start, T finish) {
        this.start = start;
        this.finish = finish;
    }

    /**
     * Gets the starting position of the movement.
     *
     * @return the starting grid position
     */
    public T getStart() {
        return start;
    }

    /**
     * Gets the finishing position of the movement.
     *
     * @return the finishing grid position
     */
    public T getFinish() {
        return finish;
    }

    /**
     * Calculates the change in the x-coordinate between the start and finish positions.
     *
     * @return the difference in x-coordinates (deltaX)
     */
    public int getDeltaX() {
        return finish.getX() - start.getX();
    }

    /**
     * Calculates the change in the y-coordinate between the start and finish positions.
     *
     * @return the difference in y-coordinates (deltaY)
     */
    public int getDeltaY() {
        return finish.getY() - start.getY();
    }

    /**
     * Checks if the movement is valid within the context of the specified track.
     * A movement is considered valid if the finish position is not out of the track bounds
     * and is not marked as OUT_OF_TRACK.
     *
     * @param track the track on which the movement is being validated
     * @return true if the movement is valid, false otherwise
     */
    public boolean isValid(Track<CircuitField> track) {
        CircuitField circuit = track.getCell(finish.getX(), finish.getY());
        return circuit != null && circuit.getCarType() != RaceCar.OUT_OF_TRACK;
    }
}
