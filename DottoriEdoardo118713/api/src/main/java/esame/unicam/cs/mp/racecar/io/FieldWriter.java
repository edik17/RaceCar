package esame.unicam.cs.mp.racecar.io;

import esame.unicam.cs.mp.racecar.model.CarState;
import esame.unicam.cs.mp.racecar.model.Circuit.CircuitState;
import esame.unicam.cs.mp.racecar.model.Environment;
import esame.unicam.cs.mp.racecar.model.Location;

import java.util.stream.Collectors;

public class FieldWriter <C extends Location<C>> implements EnvironmentWriter<CircuitState, C> {
    private final LocationWriter<C> locationWriter;

    /**
     * Creates a Writer for Circuit fields.
     *
     * @param locationWriter writer used to serialize the used location.
     * */
    public FieldWriter(LocationWriter<C> locationWriter) {
        this.locationWriter = locationWriter;
    }

    public String stringOf(Environment<CircuitState, C> field) {
        return field.getLocations(CircuitState.IN_TRACK).stream().map(locationWriter::stringOf).collect(Collectors.joining("\n"));
    }
}
