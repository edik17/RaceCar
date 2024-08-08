package esame.unicam.cs.mp.racecar.app.io;

import esame.unicam.cs.mp.racecar.app.model.CarState;
import esame.unicam.cs.mp.racecar.app.model.Environment;
import esame.unicam.cs.mp.racecar.app.model.Location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is a functional interface used to write the environment into a String
 *
 * @param <S>
 * @param <C>
 **/
@FunctionalInterface
public interface EnvironmentWriter<S extends CarState, C extends Location<C>> {

    /**
     * Returns the String rapresenting the given track
     *
     * @param track a track
     * @return the String representing the given field
     * */
    String stringOf(Environment<S, C> track);

    /**
     * Writes the given field in the file referenced by the given path
     *
     * @param path the path where the track is saved
     * @param track the track to write
     * @throws IOException if an I/O error occurs while writing the file
     * */
    default void writeTo(Path path, Environment<S, C> track) throws IOException {
        Files.write(path, stringOf(track).getBytes());
    }

    /**
     * Writes the given track in the given file
     *
     * @param file the file where the track is saved
     * @param track the track to write
     * @throws IOException if an I/O error occurs while writing the file
     * */
    default void writeTo(File file, Environment<S, C> track) throws IOException{
        writeTo(file.toPath(), track);
    }
}
