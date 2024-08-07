package esame.unicam.cs.mp.racecar.model.Circuit;

import esame.unicam.cs.mp.racecar.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * This Rule implements the check if the car is on the track. If the car is not on the track, the game is over.
 * */
public class IsOnTrackRule implements Rule<CircuitState> {

    public Optional<CircuitState> apply(CircuitState carState, List<CircuitState> neighboursStatus) {
        if (carState.isOutOfTrack()) {
            return Optional.of(CircuitState.OUT_OF_TRACK);
        }
        return Optional.empty();
    }

}
