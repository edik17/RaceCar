package esame.unicam.cs.mp.racecar.app.model.Circuit;

import esame.unicam.cs.mp.racecar.app.model.Rule;

import java.util.List;
import java.util.Optional;

public class InertiaRule implements Rule<CircuitState> {

    @Override
    public Optional<CircuitState> apply(CircuitState carState, List<CircuitState> neighboursStatus) {
        if (carState.isNearTurn()) {
            return Optional.of(CircuitState.INERTIA_TURN);
        }
        return Optional.empty();
    }
}
