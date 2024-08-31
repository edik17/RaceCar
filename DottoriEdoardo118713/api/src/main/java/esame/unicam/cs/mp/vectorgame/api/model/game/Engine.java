package esame.unicam.cs.mp.vectorgame.api.model.game;

import java.util.List;

/**
 * The {@code Engine} interface represents the core logic and control mechanism
 * for the game. It defines the essential methods that any game engine should
 * implement to manage the state and behavior of the game, including tracking
 * players and their interactions within the game's environment.
 *
 * <p>The engine is responsible for maintaining the list of players, executing
 * game logic, and providing updates to ensure the game's state is consistent.
 * Implementing classes will provide specific game logic based on the type of
 * grid and players used.</p>
 *
 * @param <E> the type of grid used by the game engine, which extends {@link Grid}.
 *            This allows the engine to manage different kinds of game environments.
 */
public interface Engine<E extends Grid<E>> {

    /**
     * Retrieves the list of players currently participating in the game.
     * This method provides access to all players, enabling the game engine
     * to iterate over the players for updates, state checks, and interactions.
     *
     * <p>This list is essential for game operations such as determining the
     * next move for each player, checking if any player has won, or if the
     * game is over. Implementing classes should ensure this list is up-to-date
     * and reflects the current state of the game.</p>
     *
     * @return a list of {@link Player} objects representing all the players in the game.
     *         The list may be empty if no players are present or if the game has ended.
     */
    List<Player<E>> getPlayers();
}
