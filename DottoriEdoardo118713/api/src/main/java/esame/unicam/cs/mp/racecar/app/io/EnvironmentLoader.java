package esame.unicam.cs.mp.racecar.app.io;

import esame.unicam.cs.mp.racecar.app.model.CarState;
import esame.unicam.cs.mp.racecar.app.model.Environment;
import esame.unicam.cs.mp.racecar.app.model.Location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * this is a functional interface that is used to read the environment from a String.
 *
 * @param <S>
 * @param <C>
 * */
@FunctionalInterface
public interface EnvironmentLoader<S extends CarState, C extends Location<C>> {
    /**
     * Parse a String that contains the description of a Race car environment.
     *
     * @param content string containing the description of the environment.
     * @return the environment associated with the given string.
     * @throws IOException if the string is not well formed.
     * */
    Environment<S, C> parse(String content) throws IOException;


    /**
     * Returns the environment stored in the file referenced by the given path.
     *
     * @param path a path to the file containing a Race car environment.
     * @return the environment described in the file referenced by the given path.
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read.
     */
    default Environment<S,C>   parse(Path path) throws IOException {
        return parse(Files.readString(path));
    }

    /**
     * Returns the environment stored in the given file.
     *
     * @param file a file containing a Race Car environment
     * @return the environment described in the file referenced by the given path.
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read.
     */
    default Environment<S,C>  parse(File file) throws IOException {
        return parse(file.toPath());
    }

}
