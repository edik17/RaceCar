package esame.unicam.cs.mp.vectorgame.api.model.game;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;

public interface IMovement<T extends Grid<T>>{
    /**
     * Retrieves the starting cell of the movement.
     *
     * @return The starting cell of the move, where the movement originates.
     */
    T getStart();

    /**
     * Retrieves the ending cell of the movement.
     *
     * @return The cell where the movement concludes.
     */
    T getFinish();

    /**
     * Calculates the horizontal displacement caused by the move.
     *
     * @return The difference in the x-coordinate between the end and start cells.
     */
    int getDeltaX();

    /**
     * Calculates the vertical displacement caused by the move.
     *
     * @return The difference in the y-coordinate between the end and start cells.
     */
    int getDeltaY();

    /**
     * Validates the move within the context of the specified track. This method checks if the move
     * is allowed based on the track's conditions and rules, such as barriers, track boundaries,
     * or specific track rules.
     *
     * @param track The track on which the move is being made, typically involving specific layout and rules.
     * @return true if the move is valid within the given track context; false otherwise.
     */
    boolean isValid(Track<CircuitField> track);
}
