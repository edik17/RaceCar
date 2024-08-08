package esame.unicam.cs.mp.racecar.app.model;

import java.util.Map;

/**
 * This interface is implemented by observers of a racecar execution.
 * */
public interface ExecutionListener<S, C extends Location<C>> {

        /**
        * Invoked when an execution step is performed.
        *
        * @param carPosition current carPosition position.
        * @param updates cell updated in the last step.
        */
        void executionStep(int carPosition, Map<C, S> updates);
}
