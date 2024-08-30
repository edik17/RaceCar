package esame.unicam.cs.mp.vectorgame.api.model.game;

import java.util.List;

public interface Grid<C extends Grid<C>> {
    /**
     * Retrieves the x-coordinate of this cell within the grid.
     *
     * @return the x-coordinate representing the horizontal position of the cell in the grid.
     */
    int getX();

    /**
     * Retrieves the y-coordinate of this cell within the grid.
     *
     * @return the y-coordinate representing the vertical position of the cell in the grid.
     */
    int getY();

    /**
     * Gets the type of the cell as defined in {@link RaceCar}. This type determines the
     * cell's properties and how it interacts within the game mechanics.
     *
     * @return the {@link RaceCar} enumeration value representing the type of this cell.
     */
    RaceCar getCarType();

    /**
     * Provides a list of neighboring cells. This is crucial for determining possible moves
     * and interactions with other cells based on the game's rules.
     *
     * @return a list of neighboring cells of the same type as this cell.
     */
    List<C> getNeighbors();

    /**
     * Adds a neighboring cell to this cell's list of neighbors. This method is typically used
     * during the initialization or updating of the game grid to set up direct relationships
     * between adjacent cells.
     *
     * @param neighbor the cell to be added as a neighbor to this cell.
     */
    void addNeighbor(C neighbor);
}
