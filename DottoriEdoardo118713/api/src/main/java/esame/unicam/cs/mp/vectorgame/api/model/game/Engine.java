package esame.unicam.cs.mp.vectorgame.api.model.game;

import java.util.List;

public interface Engine<E extends Grid<E>> {

    /**
     * Executes one cycle or turn of the game. This method should contain the logic to process player moves,
     * check for game-ending conditions, and update the game state accordingly.
     */
    void play();

    /**
     * Retrieves the list of players currently participating in the game.
     * This list is essential for game operations, allowing for iteration over all players for updates and checks.
     *
     * @return a list of {@link Player} objects representing all the players in the game.
     */
    List<Player<E>> getPlayers();

    /**
     * Determines if the game has reached a condition where it should be terminated.
     * This could be due to all players finishing, a player winning, or other end-game conditions being met.
     *
     * @return true if the game should no longer continue, false otherwise.
     */
    boolean isTerminated();
}
