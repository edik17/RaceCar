package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.IMovement;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

public class Movement<T extends Grid<T>> implements IMovement<T> {

    private final T start;
    private final T finish;

    public Movement(T start, T finish) {
        this.start = start;
        this.finish = finish;
    }

    public T getStart() {
        return start;
    }

    public T getFinish() {
        return finish;
    }

    public int getDeltaX() {
        return finish.getX() - start.getX();
    }

    public int getDeltaY() {
        return finish.getY() - start.getY();
    }

    public boolean isValid(Track<CircuitField> track) {
        CircuitField circuit = track.getCell(finish.getX(), finish.getY());
        return circuit != null && circuit.getCarType() != RaceCar.OUT_OF_TRACK;
    }
}
