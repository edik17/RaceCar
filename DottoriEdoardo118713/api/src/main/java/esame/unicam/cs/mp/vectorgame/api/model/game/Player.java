package esame.unicam.cs.mp.vectorgame.api.model.game;

import esame.unicam.cs.mp.vectorgame.api.model.Movement;

public abstract class Player<T extends Grid<T>> implements Gamer<T> {
    private String name;
    private T currentPosition;
    private Movement<T> lastMove;
    private final Track<T> track;
    boolean finished = true;

    public Player(String name, T startPosition, Track<T> track) {
        this.name = name;
        this.currentPosition = startPosition;
        this.track = track;
        this.lastMove = null;
    }

    public String getName() {
        return name;
    }

    public T getCurrentPosition() {
        return currentPosition;
    }

    public Movement<T> getLastMove() {
        return lastMove;
    }

    public Track<T> getTrack() {
        return track;
    }

    public void setPosition(T position) {
        this.lastMove = new Movement<> (this.currentPosition, position);
        this.currentPosition = position;
    }

    public boolean hasCrashed(){
        return this.currentPosition == null ||
                this.currentPosition.getCarType().equals(RaceCar.OUT_OF_TRACK);
    }

    public boolean hasFinished(){
        if(this.currentPosition == null) return false;
        return this.currentPosition.getCarType().equals(RaceCar.FINISH);
    }

    public abstract T move();

    public abstract T adjacentMove();

    public void setName(String name) {
        this.name = name;
    }


    public boolean isFinished() {

        return finished;
    }
}
