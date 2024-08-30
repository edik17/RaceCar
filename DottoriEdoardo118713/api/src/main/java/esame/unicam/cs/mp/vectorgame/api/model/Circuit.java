package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Circuit implements Track<CircuitField> {
    private final List<CircuitField> grid;
    private final int w, h;

    public Circuit(int w, int h) {
        this.w = w;
        this.h = h;
        this.grid = new ArrayList<>();
    }

    public void addCells(CircuitField cell) {
        grid.add(cell);
    }

    public CircuitField getCell(int x, int y) {
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return null;
        }
        return grid.stream()
                .filter(grid -> grid.getX() == x && grid.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public List<CircuitField> getStartGridPosition(){
        return grid.stream().filter(grid -> grid.getCarType() == RaceCar.START)
                .collect(Collectors.toList());
    }

    public CircuitField[][] getGridAsMatrix(){
        CircuitField[][] matrix = new CircuitField[w][h];
        grid.forEach(cell -> matrix[cell.getX()][cell.getY()] = cell);
        return matrix;
    }

    /**
     * method to check if the car is out of the track
     *
     * @param newPosition
     * @return
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
     * Returns the field at the specified coordinates
     *
     * @param newX
     * @param newY
     * @return
     */
    @Override
public CircuitField getField(int newX, int newY) {
    return getCell(newX, newY);
}

}
