package esame.unicam.cs.mp.racecar.app.model.Circuit;

import esame.unicam.cs.mp.racecar.app.model.CarState;
import esame.unicam.cs.mp.racecar.app.model.Rule;
import esame.unicam.cs.mp.racecar.app.model.ApplyFirst;

public enum CircuitState implements CarState {
    /**
     * The statte of a in track car
     * */
    IN_TRACK,

    /**
     * The state of a out of track car
     * */
    OUT_OF_TRACK,

    /**
     * Inertia rule for the car
     * */
    INERTIA_TURN;

    /**
     * Default rules for Car models.
     * */
    public final static Rule<CircuitState> DEFAULT_RULES = new ApplyFirst<>(new MaintainSpeedRule(), new IsOnTrackRule(), new WinnerRule(), new InertiaRule());

    /**
     * Returns true if this state represents a car in track.
     *
     * @return true if this state represents a car in track.
     */
    public boolean isOnTrack() {
        return this == IN_TRACK;
    }

    /**
     * Returns true if this state represents a car out of track.
     *
     * @return true if this state represents a car out of track.
     */
    public boolean isOutOfTrack() {
        return this == OUT_OF_TRACK;
    }

    /**
     * Returns true if this state represents a car near a turn.
     *
     * @return true if this state represents a car near a turn.
     * */
    public boolean isNearTurn() {
        return this == INERTIA_TURN;
    }

    /**
     * Return true if the car is in track
     *
     * @return true if the car is in track
     * */
    public CircuitState swap() {
        return switch (this) {
            case IN_TRACK -> IN_TRACK;
            case OUT_OF_TRACK -> OUT_OF_TRACK;
            case INERTIA_TURN -> INERTIA_TURN;
        };
    }
}
