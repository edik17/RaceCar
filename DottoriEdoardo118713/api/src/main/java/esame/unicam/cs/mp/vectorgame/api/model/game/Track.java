package esame.unicam.cs.mp.vectorgame.api.model.game;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;

import java.util.List;

/**
 * Represents the interface for a track in the Formula 1 simulation game.
 * It defines methods for managing the cells that make up the track, retrieving specific cells,
 * and obtaining overall track dimensions and starting positions.
 *
 * @param <T> the specific type of cells that make up the track, extending {@link Grid}.
 */
public interface Track<T extends Grid<T>> {

    /**
     * Adds a cell to the track. This method is used to populate the track with cells during initialization or updates.
     *
     * @param cell The cell to be added to the track.
     */
    void addCells(T cell);

    /**
     * Retrieves a specific cell from the track based on its x and y coordinates.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell at the specified coordinates, or null if no cell exists at those coordinates.
     */
    T getCell(int x, int y);

    /**
     * Gets the width of the track, typically measured in the number of cells along the x-axis.
     *
     * @return The width of the track.
     */
    int getWidth();

    /**
     * Gets the height of the track, typically measured in the number of cells along the y-axis.
     *
     * @return The height of the track.
     */
    int getHeight();

    /**
     * Retrieves a list of starting position cells on the track. These cells are where players begin the race.
     *
     * @return A list of cells designated as starting positions.
     */
    List<T> getStartGridPosition();

    /**
     * Provides a two-dimensional array representation of the cells that make up the track.
     * This can be useful for visualizations or algorithms that require a matrix form of the track.
     *
     * @return A 2D array of {@link CircuitField} objects representing the layout of the track.
     */
    CircuitField[][] getGridAsMatrix();

    /**
     * Checks if the specified position is outside the boundaries of the track.
     * This method is useful for determining whether a move or placement of an object
     * is valid within the confines of the track.
     *
     * @param newPosition The position to check.
     * @return true if the specified position is out of the track, false otherwise.
     */
    boolean isOutOfTrack(T newPosition);

    /**
     * Retrieves the cell at the specified coordinates. This method is a more specific
     * retrieval operation, potentially useful for accessing neighboring cells or performing
     * localized operations on the track.
     *
     * @param newX The x-coordinate of the cell.
     * @param newY The y-coordinate of the cell.
     * @return The cell at the specified coordinates, or null if no cell exists at those coordinates.
     */
    T getField(int newX, int newY);
}
