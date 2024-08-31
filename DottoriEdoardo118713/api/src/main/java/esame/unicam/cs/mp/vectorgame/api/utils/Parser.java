package esame.unicam.cs.mp.vectorgame.api.utils;

import esame.unicam.cs.mp.vectorgame.api.TrackController;
import esame.unicam.cs.mp.vectorgame.api.model.Circuit;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Parser class is responsible for reading and parsing a configuration file
 * to create a Circuit object representing the track. It reads the dimensions
 * of the track, initializes each cell based on provided symbols, and sets up
 * neighboring relationships between cells for pathfinding and movement logic.
 */
public class Parser {

    /**
     * Parses a configuration file to create a Circuit object. This method reads the file line by line,
     * interprets each character as a type of cell on the track, and constructs a Circuit object
     * with those cells. The first line of the file should contain the dimensions of the track.
     *
     * @param br the BufferedReader used to read the configuration file
     * @return a Circuit object representing the parsed track
     * @throws IOException if the file is empty, improperly formatted, or cannot be read
     */
    public Circuit parseFile(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            throw new IOException("Configuration file is empty or invalid");
        }
        String[] dimensions = line.split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        Circuit track = new Circuit(width, height);

        for (int y = 0; y < height; y++) {
            line = br.readLine();
            if (line == null || line.length() != width) {
                throw new IOException("Line dimension is invalid or file is incomplete");
            }
            for (int x = 0; x < width; x++) {
                RaceCar cellType = RaceCar.getRaceCarFromSymbol(line.charAt(x));
                CircuitField cell = new CircuitField(x, y, cellType);
                track.addCells(cell);
            }
        }

        setNeighbors(track);
        return track;
    }

    /**
     * Sets the neighboring cells for each cell in the circuit.
     * This method is used to establish adjacency relationships, which are crucial
     * for determining valid movements and interactions between cells.
     *
     * @param track the Circuit object for which neighbors are to be set
     */
    private void setNeighbors(Circuit track) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        for (int y = 0; y < track.getHeight(); y++) {
            for (int x = 0; x < track.getWidth(); x++) {
                CircuitField cell = track.getCell(x, y);
                if (cell == null) continue;
                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    CircuitField neighbor = track.getCell(nx, ny);
                    if (neighbor != null) {
                        cell.addNeighbor(neighbor);
                    }
                }
            }
        }
    }
}
