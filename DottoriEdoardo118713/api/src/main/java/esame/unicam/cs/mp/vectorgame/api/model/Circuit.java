package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Circuit class represents a track for the Formula 1 simulation game.
 * It implements the Track interface using CircuitField cells. The Circuit can be
 * queried for specific cells, track dimensions, start positions, and can determine if
 * a position is out of the track.
 */
public class Circuit implements Track<CircuitField> {
    private final List<CircuitField> grid;
    private final int w, h;

    /**
     * Constructs a Circuit with the specified width and height.
     *
     * @param w the width of the circuit
     * @param h the height of the circuit
     */
    public Circuit(int w, int h) {
        this.w = w;
        this.h = h;
        this.grid = new ArrayList<>();
    }

    /**
     * Adds a cell to the circuit. This cell becomes part of the track's grid.
     *
     * @param cell the cell to be added to the track
     */
    public void addCells(CircuitField cell) {
        grid.add(cell);
    }

    /**
     * Retrieves a specific cell from the track based on its x and y coordinates.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return the cell at the specified coordinates, or null if no cell exists at those coordinates
     */
    public CircuitField getCell(int x, int y) {
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return null;
        }
        return grid.stream()
                .filter(grid -> grid.getX() == x && grid.getY() == y)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the width of the track, measured in the number of cells along the x-axis.
     *
     * @return the width of the track
     */
    public int getWidth() {
        return w;
    }

    /**
     * Gets the height of the track, measured in the number of cells along the y-axis.
     *
     * @return the height of the track
     */
    public int getHeight() {
        return h;
    }

    /**
     * Retrieves a list of starting position cells on the track.
     * These cells are where players begin the race.
     *
     * @return a list of cells designated as starting positions
     */
    public List<CircuitField> getStartGridPosition() {
        return grid.stream().filter(grid -> grid.getCarType() == RaceCar.START)
                .collect(Collectors.toList());
    }

    /**
     * Provides a two-dimensional array representation of the cells that make up the track.
     * This can be useful for visualizations or algorithms that require a matrix form of the track.
     *
     * @return a 2D array of CircuitField objects representing the layout of the track
     */
    public CircuitField[][] getGridAsMatrix() {
        CircuitField[][] matrix = new CircuitField[w][h];
        grid.forEach(cell -> matrix[cell.getX()][cell.getY()] = cell);
        return matrix;
    }

    /**
     * Checks if the specified position is out of the track boundaries or marked as OUT_OF_TRACK.
     *
     * @param newPosition the position to check
     * @return true if the specified position is out of the track, false otherwise
     */
    @Override
    public boolean isOutOfTrack(CircuitField newPosition) {
        if (newPosition == null) {
            return true;
        }

        int x = newPosition.getX();
        int y = newPosition.getY();

        // Check if the position is within the boundaries
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return true;
        }

        // Check if the cell is marked as OUT_OF_TRACK
        CircuitField cell = getCell(x, y);
        return cell == null || cell.getCarType() == RaceCar.OUT_OF_TRACK;
    }

    /**
     * Returns the field at the specified coordinates.
     * This method is useful for accessing neighboring cells or performing localized operations on the track.
     *
     * @param newX the x-coordinate of the cell
     * @param newY the y-coordinate of the cell
     * @return the cell at the specified coordinates, or null if no cell exists at those coordinates
     */
    @Override
    public CircuitField getField(int newX, int newY) {
        return getCell(newX, newY);
    }
}
