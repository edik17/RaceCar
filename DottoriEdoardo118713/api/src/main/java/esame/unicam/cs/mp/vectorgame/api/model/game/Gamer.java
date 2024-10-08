package esame.unicam.cs.mp.vectorgame.api.model.game;

/**
 * The {@code Gamer} interface represents a participant in the game, typically a player,
 * and defines essential methods that every player must implement. These methods handle
 * the player's movement, position, and state within the game. Implementing classes will
 * define the specific behavior for different types of players, such as human players or bots.
 *
 * @param <T> the type of grid used by the gamer, which extends {@link Grid}.
 *            This allows the gamer to interact with different kinds of game environments.
 */
public interface Gamer<T extends Grid<T>> {

    /**
     * Gets the current position of the player on the track.
     *
     * @return The current cell where the player is located.
     */
    T getCurrentPosition();

    /**
     * Returns the last move made by the player.
     *
     * @return An {@link IMovement} object representing the last move made by the player, or null if no moves have been made.
     */
    IMovement<T> getLastMove();

    /**
     * Retrieves the track on which the player is racing.
     *
     * @return An {@link Track} object representing the track associated with the player.
     */
    Track<T> getTrack();

    /**
     * Sets the position of the player to a new cell.
     *
     * @param newPosition The new cell to which the player will move.
     */
    void setPosition(T newPosition);

    /**
     * Determines whether the player has crashed, based on the game's rules and the player's current cell.
     *
     * @return true if the player has crashed, false otherwise.
     */
    boolean hasCrashed();

    /**
     * Checks if the player has finished the race by reaching the finish line.
     *
     * @return true if the player has finished the race, false otherwise.
     */
    boolean hasFinished();

    /**
     * Calculates an adjacent move from the player's current position. This method is typically used
     * to determine possible small adjustments or evasive maneuvers adjacent to the main track path.
     *
     * @return An {@link Grid} object representing a cell adjacent to the current position, or null if no such move is valid.
     */
    Grid<T> adjacentMove();

    /**
     * Calculates the main move for the player based on their last movement and current position. This is generally
     * the primary method of progression on the track.
     *
     * @return An {@link Grid} object representing the cell to which the player will move next, under normal conditions.
     */
    Grid<T> move();
}
