package esame.unicam.cs.mp.racecar.app.model;


import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Classes implementing this interface are used to represent the environment where the cars
 *
 * @param <S> type representing the status of cars
 * @param <C> type representing the position of cars in the environment
 * */
public interface Environment<S extends CarState, C extends Location<C>> {

    /**
     * Return the state of the car located at the given location
     *
     * @param loc a location
     * @return the status of the car located at the given location
     * */
    S statusOf(C loc);

    /**
     * Given a set of locations, returns the map associating to each of its state
     *
     * @param locs a set of locations
     * @return the map associating to each of the location in the given set with its state
     * */
    Map<C,S> getStatusMapOf(Collection<C> locs);

    /**
     * Returns a list containing the states of the cars at the given locations
     *
     * @param locs a collection of locations
     * @return a list containing the states of the cars at the given locations
     * */
    List<S> statusOf(Collection<C> locs);

    /**
     * Returns a map containing the cars that are changing their state due to the application of the given rule
     *
     * @param rule a rule describing the evolution of the environment
     * @return the environment obtained from this one by applying the given rule
     * */
    Map<C, S> evolvingCars(Rule<S> rule);

    /**
     * Returns the environment obtained from this one by applying the given rule
     *
     * @param rule a rule describing the evolution of the environment
     * @return the environment obtained from this one by applying the given rule
     * */
    default Environment<S, C> apply(Rule<S> rule) {
        return set(evolvingCars(rule));
    }

    /**
     * Retruns the environment obtained from this one by setting the status of the cars at the given locations
     *
     * @param updates a map associating to each location the new status of the car
     * @return the environment obtained from this one by setting the status of the cars at the given locations
     * */
    Environment<S, C> set(Map<C, S> updates);

    /**
     * Sets the state of the car at the given location
     *
     * @param loc a location
     * @param state the new state of the car
     * */
    void set(C loc, S state);

    /**
     * Return the list of locations containg the cars with the given state. Whenever <code>state.isOnTrack()</code>
     * the retrurnig list will be empty.
     *
     * @param state the state of the cars
     * @return the list of locations containing the cars with the given state
     * */
    List<C> getLocations(S state);
}
