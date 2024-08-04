package esame.unicam.cs.mp.racecar.model.Circuit;

import esame.unicam.cs.mp.racecar.model.CarState;
import esame.unicam.cs.mp.racecar.model.Rule;

import java.util.List;
import java.util.Optional;

/**
 * Rule implementing the maintaining speed rule: The car must maintain a constant speed.
 * */
public class MaintainSpeedRule implements Rule<CircuitState> {

    @Override
    public Optional<CircuitState> apply(CircuitState carState, List<CircuitState> neighboursStatus) {
        if (carState == CircuitState.IN_TRACK) {
            long counter = neighboursStatus.stream().filter(CircuitState::isOnTrack).count();
            if (counter == 2 || counter == 3) {
                return Optional.of(CircuitState.IN_TRACK);
            }
        }
        return Optional.empty();
    }

}
