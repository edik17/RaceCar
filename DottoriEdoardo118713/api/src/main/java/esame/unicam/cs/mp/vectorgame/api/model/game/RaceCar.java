package esame.unicam.cs.mp.vectorgame.api.model.game;

public enum RaceCar {
    /**
     * CircuitField type representing a track where players can drive.
     */
    TRACK('.'),

    /**
     * CircuitField type representing the starting position on the track.
     */
    START('S'),

    /**
     * CircuitField type representing the finish line on the track.
     */
    FINISH('#'),

    /**
     * CircuitField type representing areas off the track where driving is not allowed.
     */
    OUT_OF_TRACK('*');

    private final char s;

    RaceCar(char s) {
        this.s = s;
    }

    public char getSymbol() {
        return s;
    }

    public static RaceCar getRaceCarFromSymbol(char s) {
        for (RaceCar rc : values()) {
            if (rc.getSymbol() == s) {
                return rc;
            }
        }
        return OUT_OF_TRACK;
    }
}
