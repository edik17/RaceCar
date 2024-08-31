package esame.unicam.cs.mp.vectorgame.api.model.game;

/**
 * The {@code RaceCar} enum represents different types of track segments or areas
 * on a racing circuit. Each type is associated with a specific character symbol,
 * which is used to identify and differentiate the track segments.
 * This enum provides utility methods for retrieving the symbol and mapping symbols to track types.
 */
public enum RaceCar {

    /**
     * CircuitField type representing a track where players can drive.
     * This type is generally used for the main racing area, allowing
     * players to move and race freely.
     */
    TRACK('.'),

    /**
     * CircuitField type representing the starting position on the track.
     * Players begin the race from this type of cell. Typically, there are
     * multiple starting positions depending on the number of players.
     */
    START('S'),

    /**
     * CircuitField type representing the finish line on the track.
     * Players aim to reach this cell to finish the race. The game usually
     * recognizes the player crossing this cell as a winner.
     */
    FINISH('#'),

    /**
     * CircuitField type representing areas off the track where driving is not allowed.
     * These areas act as boundaries and obstacles, preventing players from
     * moving outside the designated track area. Colliding with these cells
     * may result in penalties or crashes.
     */
    OUT_OF_TRACK('*');

    // Private field to store the character symbol representing the track type
    private final char s;

    /**
     * Constructor to initialize the {@code RaceCar} enum with a specific character symbol.
     *
     * @param s the character symbol representing the track type
     */
    RaceCar(char s) {
        this.s = s;
    }

    /**
     * Retrieves the character symbol associated with the track type.
     *
     * @return the character symbol representing the track type
     */
    public char getSymbol() {
        return s;
    }

    /**
     * Maps a character symbol to a corresponding {@code RaceCar} type.
     * This method is useful for parsing track layouts defined using character symbols.
     * If the symbol does not match any defined track type, it defaults to {@code OUT_OF_TRACK}.
     *
     * @param s the character symbol to map
     * @return the {@code RaceCar} type corresponding to the given symbol
     */
    public static RaceCar getRaceCarFromSymbol(char s) {
        for (RaceCar rc : values()) {
            if (rc.getSymbol() == s) {
                return rc;
            }
        }
        return OUT_OF_TRACK; // Default to OUT_OF_TRACK if no match is found
    }
}
