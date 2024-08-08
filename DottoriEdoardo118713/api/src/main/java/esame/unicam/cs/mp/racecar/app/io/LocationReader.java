package esame.unicam.cs.mp.racecar.app.io;


import esame.unicam.cs.mp.racecar.app.model.Location;

import java.io.IOException;

/**
 * This interface is used to transform a location into a string.
 *
 * @param <C> type of read location.
 */
@FunctionalInterface
public interface LocationReader<C extends Location<C>> {

    /**
     * Returns the location associated with the given string.
     *
     * @param str a string containing a location.
     * @return the location associated with the given string.
     */
    C parse(String str) throws IOException;
}
