package esame.unicam.cs.mp.racecar.app.model;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This functional interface is used to compute the next state of a car given its current state and
 * the states of all its neighbours.
 *
 * @param <S> car state
 * */
@FunctionalInterface
public interface Rule <S> {
    /**
     * Returns the result of the application of this rule to a car given its current state and the states of all its neighbours.
     * If the rule cannot be applied in this configuration, an empty {@link Optional<S>} is returned.
     *
     * @param carStatus the state of a car
     * @param neighboursStatus the states of the neighbours
     * @return the next state of a car give its current state and the states of all its neighbours.
     * */
    Optional<S> apply(S carStatus, List<S> neighboursStatus);

    /**
     * Return the result of the application of this rule to a car at the given location whose state is obtained by the given
     * function <code>locationSolver</code>.
     *
     * @param loc a location.
     * @param locationSolver function used to associate each location with the state of if car.
     * @param <C> type of locations.
     * @return the result of the application of this rule to a car at the given location.
     * */
    default <C extends Location<C>> Optional<S> apply(C loc, Function<? super C, ? extends S> locationSolver) {
        return apply(locationSolver.apply(loc), loc.neighbours().stream().map(locationSolver).collect(Collectors.toList()));
    }

    /**
     * Returns the next state of a car given its current state and the states of its neighbour obtained by applying this rule.
     * If this rule cannot be applied, the car current state is returned.
     *
     * @param carStatus the state of a car
     * @param neighboursStatus the states of the neighbours
     * @return the next state of a car give its current state and the states of all its neighbours.
     * */
    default S next(S carStatus, List<S> neighboursStatus) {
        return apply(carStatus, neighboursStatus).orElse(carStatus);
    }
}
