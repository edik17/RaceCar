package esame.unicam.cs.mp.racecar.model.Circuit;

import esame.unicam.cs.mp.racecar.model.Environment;
import esame.unicam.cs.mp.racecar.model.Location;
import esame.unicam.cs.mp.racecar.model.Rule;
import esame.unicam.cs.mp.racecar.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CircuitField <L extends Location<L>> implements Environment<CircuitState, L> {

    /**
     * This set is used to store cars that are in track.
     */
    private final Set<L> inTrackCars;

    /**
     * Create an empty field for the Circuit model.
     * */
    public CircuitField() {
       this(new HashSet<>());
    }

    /**
     * Create a field for the Circuit model with the given cars.
     *
     * @param inTrackCars the set of cars that are in track.
     * */
    protected CircuitField(Set<L> inTrackCars) {
        this.inTrackCars = inTrackCars;
    }

    /**
     * A copy constructor.
     *
     * @param circuitField field to copy.
     * */
    private CircuitField(CircuitField<L> circuitField) {
        this(new HashSet<>(circuitField.inTrackCars));
    }

    @Override
    public CircuitState statusOf(L loc) {
        return (this.inTrackCars.contains(loc)?CircuitState.IN_TRACK: CircuitState.OUT_OF_TRACK);
    }

    @Override
    public Map<L, CircuitState> getStatusMapOf(Collection<L> set) {
        return set.stream().collect(Collectors.toMap(l -> l, this::statusOf));
    }

    @Override
    public List<CircuitState> statusOf(Collection<L> locs) {
        return locs.stream().map(this::statusOf).collect(Collectors.toList());
    }

    @Override
    public Map<L, CircuitState> evolvingCars(Rule<CircuitState> rule){
        return Stream.concat(
                this.inTrackCars.stream(),
                this.inTrackCars.stream().map(Location::neighbours).flatMap(List::stream)
                ).distinct()
                .map(l -> new Pair<>(l, rule.apply(l, this::statusOf)))//Apply the rule to all locations
                .filter(p -> p.testSecond(Optional::isPresent))//Remove elements where the rule has not been applied
                .map(p -> p.map(Optional::get))
                .filter(p -> p.test((l,s) -> statusOf(l) != s))//Remove location that have not changed
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    @Override
    public Environment<CircuitState, L> set(Map<L, CircuitState> updates) {
        CircuitField<L> newField = new CircuitField<>(this);
        updates.forEach(newField::set);
        return newField;
    }

    @Override
    public List<L> getLocations(CircuitState state){return List.copyOf(inTrackCars);}

    @Override
    public void set(L loc, CircuitState state) {
        if(state.isOnTrack())
            setIsOnTrack(loc);
        else
            setOutOfTrack(loc);
    }

    /**
     * Set the car at the given location in track.
     *
     * @param loc a location.
     * */
    public void setIsOnTrack(L loc) {
        inTrackCars.add(loc);
    }

    /**
     * Set the car at the given location out of track.
     *
     * @param loc a location.
     * */
    public void setOutOfTrack(L loc) {
        inTrackCars.remove(loc);
    }

    public List<L> getInTrackCars() {
        return List.copyOf(inTrackCars);
    }
}

