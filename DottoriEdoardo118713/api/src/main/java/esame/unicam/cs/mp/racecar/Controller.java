package esame.unicam.cs.mp.racecar;

import esame.unicam.cs.mp.racecar.io.CarCircuit;
import esame.unicam.cs.mp.racecar.io.EnvironmentLoader;
import esame.unicam.cs.mp.racecar.io.EnvironmentWriter;
import esame.unicam.cs.mp.racecar.io.FieldWriter;
import esame.unicam.cs.mp.racecar.model.*;
import esame.unicam.cs.mp.racecar.model.Circuit.CircuitField;
import esame.unicam.cs.mp.racecar.model.Circuit.CircuitState;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * This class used to control the actions of the race car game execution
 * */
public class Controller <S extends CarState, C extends Location<C>> {

    private final EnvironmentWriter<S, C> writer;
    private final EnvironmentLoader<S, C> loader;

    private final Supplier<Environment<S, C>> environmentBuilder;

    private final Rule<S> rules;

    private Environment<S,C> currentField;

    private LinkedList<Environment<S,C>> history;

    public static Controller<CircuitState, GridCoordinates> getCircuitController() {
        return new Controller<>(new FieldWriter<>(GridCoordinates.WRITER), new CarCircuit<>(GridCoordinates.LOADER), CircuitField::new, CircuitState.DEFAULT_RULES);
    }

    /**
     * Creates a new controller that will used the given writer, to save and export fields, loader, to read
     * schemata from files, and rules to compute execution.
     *
     * @param writer             writer used to save fields on files.
     * @param loader             loader used to load fields from files.
     * @param environmentBuilder builder used to instantiate the environment.
     * @param rules              rules used to perform computations.
     */
    public Controller(EnvironmentWriter<S, C> writer, EnvironmentLoader<S, C> loader, Supplier<Environment<S, C>> environmentBuilder, Rule<S> rules) {
        this.writer = writer;
        this.loader = loader;
        this.environmentBuilder = environmentBuilder;
        this.rules = rules;
        this.currentField = environmentBuilder.get();
        this.history = new LinkedList<>();
    }

    /**
     * Loads the handled environment from file.
     *
     * @param file file from which we can load the environment.
     * @throws IOException if an I/O error occurs while loading the data.
     */
    public void openEnvironment(File file) throws IOException {
        this.currentField = loader.parse(file);
    }

    /**
     * Writes the handled environment to the given file.
     *
     * @param file file on which we can save the environment.
     * @throws IOException if an I/O error occurs while writing the data.
     */
    public void save(File file) throws IOException {
        writer.writeTo(file, this.currentField);
    }

    /**
     * This method is used to reset the state of this controller.
     */
    public void reset() {
        this.currentField = environmentBuilder.get();
        this.history = new LinkedList<>();
    }


    public void set(C loc, S state) {
        this.currentField.set(loc, state);
    }

    public S getStateOf(C loc) {
        return currentField.statusOf(loc);
    }

    public void open(File file) throws IOException {
        this.currentField = loader.parse(file);
        this.history = new LinkedList<>();
    }

    public void clear() {
        this.currentField = environmentBuilder.get();
        this.history = new LinkedList<>();
    }


    public void stepForward() {
        this.history.add(currentField);
        currentField = currentField.apply(rules);
    }

    public void stepBackward() {
        if (!history.isEmpty()) {
            currentField = history.removeLast();
        }
    }

}
