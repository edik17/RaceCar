package esame.unicam.cs.mp.racecar.model;

import java.util.List;

public class Grid implements Track<GridCoordinates>{
    @Override
    public List<GridCoordinates> neighbours(GridCoordinates gridCoordinates) {
        return gridCoordinates.neighbours();
    }
}
