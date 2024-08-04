package esame.unicam.cs.mp.racecar.model;

/**
 * This interface is implemented by observers of a racecar execution.
 * */
public interface ExecutionListener<S, C extends Location<C>> {

        /**
        * Invoked when an execution step is performed.
        *
        * @param cursor current cursor position.
        * @param updates cell updated in the last step.
        */
        void executionStep(int cursor, S updates);
}
