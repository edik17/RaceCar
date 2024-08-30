package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;

import java.util.ArrayList;
import java.util.List;

public class CircuitField implements Grid<CircuitField> {
    private final int x;
    private final int y;
    private final RaceCar carType;
    private List<CircuitField> neighbors;
    
    
    public CircuitField(int x, int y, RaceCar carType) {
        this.x = x;
        this.y = y;
        this.carType = carType;
        this.neighbors = new ArrayList<>();
    }

    /**
     * Gets the x-coordinate of the cell.
     *
     * @return the x-coordinate.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the cell.
     *
     * @return the y-coordinate.
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Returns the type of the cell as defined by the RaceCar enum.
     * This type is crucial for determining how players interact with the cell.
     *
     * @return the cell type.
     */
    @Override
    public RaceCar getCarType() {
        return carType;
    }
    
    
    @Override
    public List<CircuitField> getNeighbors() {
        return neighbors;
    }
    
    @Override
    public void addNeighbor(CircuitField neighbor) {
        neighbors.add(neighbor);
    }
}
