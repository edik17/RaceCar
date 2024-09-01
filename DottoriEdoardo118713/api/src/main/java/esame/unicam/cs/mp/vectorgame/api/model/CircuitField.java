package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a field (cell) on the circuit track in the Formula 1 simulation game.
 * Each CircuitField is defined by its coordinates on the grid, its type (e.g., start, finish, track, or out-of-track),
 * and its neighboring fields. This class is crucial for managing the grid layout of the track and determining
 * the behavior of race cars interacting with different parts of the track.
 */
public class CircuitField implements Grid<CircuitField> {
    private final int x;
    private final int y;
    private final RaceCar carType;
    private final List<CircuitField> neighbors;

    /**
     * Constructs a CircuitField with the specified coordinates and cell type.
     *
     * @param x       the x-coordinate of the field on the grid.
     * @param y       the y-coordinate of the field on the grid.
     * @param carType the type of the field, represented by the RaceCar enum.
     */
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

    /**
     * Gets the list of neighboring fields of this CircuitField.
     * Neighbors are fields that are adjacent to this field in the grid.
     *
     * @return a list of neighboring CircuitField objects.
     */
    @Override
    public List<CircuitField> getNeighbors() {
        return neighbors;
    }

    /**
     * Adds a neighboring field to this CircuitField.
     * This method is used to establish connections between adjacent fields on the grid.
     *
     * @param neighbor the neighboring CircuitField to be added.
     */
    @Override
    public void addNeighbor(CircuitField neighbor) {
        neighbors.add(neighbor);
    }
}
