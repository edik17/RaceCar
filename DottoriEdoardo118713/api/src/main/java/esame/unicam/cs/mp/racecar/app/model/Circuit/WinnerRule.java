package esame.unicam.cs.mp.racecar.app.model.Circuit;

import esame.unicam.cs.mp.racecar.app.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * This Rule implementig the check if the car is on the track. If the car is not on the track, the game is over.
 * */
public class WinnerRule implements Rule<CircuitState> {
    @Override
    public Optional<CircuitState> apply(CircuitState carState, List<CircuitState> neighboursStatus) {
        if (carState.isOnTrack()) {
            if (neighboursStatus.stream().filter(CircuitState::isOutOfTrack).count() == 3) {
                return Optional.of(CircuitState.OUT_OF_TRACK);
            }
        }
        return Optional.empty();
    }
}
