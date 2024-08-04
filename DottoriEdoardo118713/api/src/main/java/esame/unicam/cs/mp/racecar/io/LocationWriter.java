package esame.unicam.cs.mp.racecar.io;

import esame.unicam.cs.mp.racecar.model.Location;

/**
 * This interface is used to transform a location into a string.
 *
 * @param <C> type of read location.
 */
public interface LocationWriter<C extends Location<C>> {

        /**
        * Returns the string representing the given location.
        *
        * @param loc a location.
        * @return the string representing the given location.
        */
        String stringOf(C loc);
}
