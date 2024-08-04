package esame.unicam.cs.mp.racecar.model;

import java.util.List;

public interface Track<C> {
    List<C> neighbours(C c);
}
