package esame.unicam.cs.mp.racecar.model;

import java.util.List;

/**
 * This interface is implemented for the location of the car.
 * */
public interface Location<L> {

        /**
        * This method is used to get the list of the neighbours of the car.
        * */
        List<L> neighbours();
}
