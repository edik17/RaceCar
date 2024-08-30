package test.java.esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.Circuit;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.Movement;
import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VectorGameTest {

    /**
     * Test for {@link CircuitField} class.
     */
    private CircuitField circuitField;
    private CircuitField neighbor;
    private RaceCar carType;

    @BeforeEach
    void setUp() {
        circuitField = new CircuitField(5, 10, carType);
        neighbor = new CircuitField(6, 10, carType);
    }

    @Test
    void getXReturnsCorrectXCoordinate() {
        assertEquals(5, circuitField.getX());
    }

    @Test
    void getYReturnsCorrectYCoordinate() {
        assertEquals(10, circuitField.getY());
    }

    @Test
    void getCarTypeReturnsCorrectCarType() {
        assertEquals(carType, circuitField.getCarType());
    }

    @Test
    void getNeighborsReturnsEmptyListWhenNoNeighborsAdded() {
        assertTrue(circuitField.getNeighbors().isEmpty());
    }

    @Test
    void addNeighborAddsNeighborCorrectly() {
        circuitField.addNeighbor(neighbor);
        List<CircuitField> neighbors = circuitField.getNeighbors();
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(neighbor));
    }

    @Test
    void getNeighborsReturnsCorrectNeighborsList() {
        List<CircuitField> expectedNeighbors = new ArrayList<>();
        expectedNeighbors.add(neighbor);
        circuitField.addNeighbor(neighbor);
        assertEquals(expectedNeighbors, circuitField.getNeighbors());
    }

    /**
     * Test for {@link esame.unicam.cs.mp.vectorgame.api.model.Circuit} class.
     */
    private Circuit circuit;
    private CircuitField cell;
    private CircuitField outOfTrackCell;

    @BeforeEach
    void setUpCircuit() {
        circuit = new Circuit(10, 10);
        outOfTrackCell = new CircuitField(11, 11, RaceCar.OUT_OF_TRACK);
    }

    @Test
    void addCellsAddsCellToCircuit() {
        circuit.addCells(cell);
        assertEquals(cell, circuit.getCell(1, 1));
    }

    @Test
    void getCellReturnsCorrectCell() {
        circuit.addCells(cell);
        assertEquals(cell, circuit.getCell(1, 1));
    }

    @Test
    void getCellReturnsNullForInvalidCoordinates() {
        assertNull(circuit.getCell(100, 100));
    }

    @Test
    void getWidthReturnsCircuitWidth() {
        assertEquals(10, circuit.getWidth());
    }

    @Test
    void getHeightReturnsCircuitHeight() {
        assertEquals(10, circuit.getHeight());
    }

    @Test
    void getStartGridPositionReturnsListOfStartingPositions() {
        CircuitField startCell = new CircuitField(0, 0, RaceCar.START);
        circuit.addCells(startCell);
        List<CircuitField> startPositions = circuit.getStartGridPosition();
        assertEquals(1, startPositions.size());
        assertTrue(startPositions.contains(startCell));
    }

    @Test
    void getGridAsMatrixReturnsCorrectMatrix() {
        circuit.addCells(cell);
        CircuitField[][] matrix = circuit.getGridAsMatrix();
        assertEquals(cell, matrix[1][1]);
    }

    @Test
    void isOutOfTrackReturnsTrueForOutOfTrackPosition() {
        assertTrue(circuit.isOutOfTrack(outOfTrackCell));
    }

    @Test
    void isOutOfTrackReturnsFalseForValidPosition() {
        circuit.addCells(cell);
        assertFalse(circuit.isOutOfTrack(cell));
    }

    @Test
    void getFieldReturnsCorrectField() {
        circuit.addCells(cell);
        assertEquals(cell, circuit.getField(1, 1));
    }

    @Test
    void getFieldReturnsNullForInvalidCoordinates() {
        assertNull(circuit.getField(100, 100));
    }

    /**
     * Test for {@link esame.unicam.cs.mp.vectorgame.api.model.game.IMovement} class.
     */
    private Grid start;
    private Grid finish;
    private Movement movement;
    private Track<CircuitField> track;
    private CircuitField validField;
    private CircuitField outOfTrackField;
    @Test
    void getStartReturnsCorrectStartGrid() {
        assertEquals(start, movement.getStart());
    }

    @Test
    void getFinishReturnsCorrectFinishGrid() {
        assertEquals(finish, movement.getFinish());
    }

    @Test
    void getDeltaXReturnsCorrectDeltaX() {
        assertEquals(5, movement.getDeltaX());
    }

    @Test
    void getDeltaYReturnsCorrectDeltaY() {
        assertEquals(5, movement.getDeltaY());
    }

    @Test
    void isValidReturnsTrueForValidMovement() {
        track.addCells(validField);
        assertTrue(movement.isValid(track));
    }

    @Test
    void isValidReturnsFalseForOutOfTrackMovement() {
        track.addCells(outOfTrackField);
        assertFalse(movement.isValid(track));
    }

    @Test
    void isValidReturnsFalseForNullField() {
        assertFalse(movement.isValid(track));
    }

    /**
     * Test for {@link esame.unicam.cs.mp.vectorgame.api.model.game.Player} class.

    private Player<Grid<?>> player;
    private Grid<Grid<?>> startPosition;
    private Track<Grid<?>> track;
    private Grid<Grid<?>> newPosition;
    private Grid<Grid<?>> outOfTrackPosition;
    private Grid<Grid<?>> finishPosition;

    @BeforeEach
    void setUp() {
        startPosition = new Grid<>(0, 0);
        track = new Track<>(10, 10);
        player = new Player<>("Player1", startPosition, track) {
            @Override
            public Grid<Grid<?>> move() {
                return null;
            }

            @Override
            public Grid<Grid<?>> adjacentMove() {
                return null;
            }
        };
        newPosition = new Grid<>(1, 1);
        outOfTrackPosition = new Grid<>(2, 2);
        outOfTrackPosition.setCarType(RaceCar.OUT_OF_TRACK);
        finishPosition = new Grid<>(3, 3);
        finishPosition.setCarType(RaceCar.FINISH);
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Player1", player.getName());
    }

    @Test
    void getCurrentPositionReturnsCorrectPosition() {
        assertEquals(startPosition, player.getCurrentPosition());
    }

    @Test
    void getLastMoveReturnsNullInitially() {
        assertNull(player.getLastMove());
    }

    @Test
    void getTrackReturnsCorrectTrack() {
        assertEquals(track, player.getTrack());
    }

    @Test
    void setPositionUpdatesCurrentPosition() {
        player.setPosition(newPosition);
        assertEquals(newPosition, player.getCurrentPosition());
    }

    @Test
    void setPositionUpdatesLastMove() {
        player.setPosition(newPosition);
        Movement<Grid<?>> lastMove = player.getLastMove();
        assertNotNull(lastMove);
        assertEquals(startPosition, lastMove.getStart());
        assertEquals(newPosition, lastMove.getFinish());
    }

    @Test
    void hasCrashedReturnsTrueForOutOfTrackPosition() {
        player.setPosition(outOfTrackPosition);
        assertTrue(player.hasCrashed());
    }

    @Test
    void hasCrashedReturnsFalseForValidPosition() {
        player.setPosition(newPosition);
        assertFalse(player.hasCrashed());
    }

    @Test
    void hasFinishedReturnsTrueForFinishPosition() {
        player.setPosition(finishPosition);
        assertTrue(player.hasFinished());
    }

    @Test
    void hasFinishedReturnsFalseForNonFinishPosition() {
        player.setPosition(newPosition);
        assertFalse(player.hasFinished());
    }

    @Test
    void setNameUpdatesName() {
        player.setName("NewName");
        assertEquals("NewName", player.getName());
    }*/
}