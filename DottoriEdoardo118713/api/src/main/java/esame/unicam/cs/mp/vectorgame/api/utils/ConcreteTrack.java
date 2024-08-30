package esame.unicam.cs.mp.vectorgame.api.utils;

import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.List;
import java.util.stream.Collectors;

public class ConcreteTrack implements Track<CircuitField> {
    private final List<CircuitField> fields;
    private final List<CircuitField> startPositions;

    /**
     * Constructs a ConcreteTrack object.
     *
     * @param fields         the list of all CircuitFields in the track.
     * @param startPositions the list of starting positions (fields) on the track.
     */
    public ConcreteTrack(List<CircuitField> fields, List<CircuitField> startPositions) {
        this.fields = fields;
        this.startPositions = startPositions;
    }

    @Override
    public List<CircuitField> getStartGridPosition() {
        return startPositions;
    }

    /**
     * Adds a cell to the track. This method is used to populate the track with cells during initialization or updates.
     *
     * @param cell The cell to be added to the track.
     */
    @Override
    public void addCells(CircuitField cell) {
        fields.add(cell);
        if (cell.getCarType() == RaceCar.START) {
            startPositions.add(cell);
        }
    }

    @Override
    public CircuitField getCell(int x, int y) {
        return fields.stream()
                .filter(field -> field.getX() == x && field.getY() == y)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CircuitField[][] getGridAsMatrix() {
        int maxX = fields.stream().mapToInt(CircuitField::getX).max().orElse(0);
        int maxY = fields.stream().mapToInt(CircuitField::getY).max().orElse(0);
        CircuitField[][] matrix = new CircuitField[maxX + 1][maxY + 1];
        fields.forEach(field -> matrix[field.getX()][field.getY()] = field);
        return matrix;
    }

    @Override
    public int getWidth() {
        return fields.stream().mapToInt(CircuitField::getX).max().orElse(0) + 1;
    }

    @Override
    public int getHeight() {
        return fields.stream().mapToInt(CircuitField::getY).max().orElse(0) + 1;
    }

    /**
     * Checks if the given position is out of the track.
     *
     * @param newPosition the position to check.
     * @return true if the position is out of the track, false otherwise.
     */
    @Override
    public boolean isOutOfTrack(CircuitField newPosition) {
        return fields.stream().noneMatch(field ->
                field.getX() == newPosition.getX() &&
                        field.getY() == newPosition.getY() &&
                        field.getCarType() != RaceCar.OUT_OF_TRACK);
    }

    @Override
    public CircuitField getField(int newX, int newY) {
        return null;
    }
}