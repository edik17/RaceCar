package esame.unicam.cs.mp.racecar.app.io;


import esame.unicam.cs.mp.racecar.app.model.Circuit.CircuitField;
import esame.unicam.cs.mp.racecar.app.model.Circuit.CircuitState;
import esame.unicam.cs.mp.racecar.app.model.Environment;
import esame.unicam.cs.mp.racecar.app.model.Location;

import java.io.IOException;

/**
 * A loader used to load a CarModel. This loader assumes that the live cells are listed, one per line in the file.
 * */
public class CarCircuit<C extends Location<C>> implements EnvironmentLoader<CircuitState, C> {

    private final LocationReader<C> lineParser;

    /**
     * Creates a loader that parses each line with the given function.
     *
     * @param lineParser parser used to read a single location.
     */
    public CarCircuit(LocationReader<C>  lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Environment<CircuitState, C> parse(String content) throws IOException {
        CircuitField<C> field = new CircuitField<>();
        String[] lines = content.split("\n");
        for(int i=0; i<lines.length; i++) {
            field.setIsOnTrack(lineParser.parse(lines[i]));
            try {
                field.setIsOnTrack( lineParser.parse(lines[i]) );

            } catch (IOException e) {
                throw new IOException("Syntax error at line "+i, e);
            }
        }
        return field;
    }
}
