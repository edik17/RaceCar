package esame.unicam.cs.mp.racecar.app.model;

import esame.unicam.cs.mp.racecar.app.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This Class is used to execute a RaceTrack game model
 * 
 * @param <S> the state of the car
 * @param <C> the location of the car
 * */
public final class RaceExecutor <S extends CarState, C extends Location<C>>{
    
    private Environment<S, C> field;
    
    private final ArrayList<Pair<Map<C,S>,Map<C,S>>> updates = new ArrayList<>();
    
    private int carPosition = 0;
    private final Rule<S> rule;
    
    private final List<ExecutionListener<S,C>> listenerList;
    
    /**
     * Creates a new executor starting from the given field.
     * 
     * @param field starting configuration of this field.
     * @param rule rule used in the executor.
     * */
    public RaceExecutor(Environment<S,C> field, Rule<S> rule) {
        this.field = field;
        this.rule = rule;
        this.listenerList = new LinkedList<>();
    }
    
    /**
     * Adds a listener to this execution.
     * 
     * @param listener the listener to add.
     * */
    public synchronized void addExecutionListener(ExecutionListener<S,C> listener) {
        this.listenerList.add(listener);
    }
    
    /**
     * Returns the position of the car
     * 
     * @return the position of the car
     * */
    public int getCarPosition() {
        return carPosition;
    }

    /**
     * Returns the field selected by the carPosition.
     *
     * @return the field selected by the carPosition.
     */
    public synchronized Environment<S,C> getField() {
        return this.field;
    }

    /**
     * Performs a step forward.
     */
    public synchronized void stepForward() {
        if (carPosition<updates.size()) {
            updateField(updates.get(carPosition++).getFirst());
        } else {
            computeNextStep();
        }
    }
    /**
     * Performs a backward step.
     */
    public synchronized void stepBackward() {
        if (carPosition>0) {
            updateField(updates.get(carPosition--).getSecond());
        }
    }

    private void computeNextStep() {
        Map<C, S> forwardUpdate = field.evolvingCars(this.rule);
        Map<C, S> backwardUpdate = field.getStatusMapOf(forwardUpdate.keySet());
        this.updates.add(new Pair<>(forwardUpdate, backwardUpdate));
        this.carPosition++;
        updateField(forwardUpdate);
    }

    private synchronized void updateField(Map<C,S> updates) {
        this.field = this.field.set(updates);
        this.listenerList.forEach(lst -> lst.executionStep(this.carPosition, updates));
    }
}
