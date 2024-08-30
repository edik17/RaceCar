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

public class Parser {
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

    public Track<CircuitField> parseFileApp(BufferedReader reader) throws IOException {
        List<CircuitField> fields = new ArrayList<>();
        List<CircuitField> startPositions = new ArrayList<>();
        String line;
        int y = 0;
        while ((line = reader.readLine()) != null) {
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                RaceCar carType = RaceCar.getRaceCarFromSymbol(ch);
                CircuitField field = new CircuitField(x, y, carType);
                fields.add(field);
                if (ch == 'S' || RaceCar.START.equals(carType)) {
                    startPositions.add(field);
                }
            }
            y++;
        }
        return new ConcreteTrack(fields, startPositions);
    }
}
