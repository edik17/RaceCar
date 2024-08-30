package esame.unicam.cs.mp.vectorgame.api;

import esame.unicam.cs.mp.vectorgame.api.model.Circuit;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;

import java.io.*;
import java.util.stream.Collectors;

public class TrackController {
    private Circuit circuit;
    private int carX, carY;

    public void createTrackFromFile(String resourcePath) {
        try {
            String[] layout = readTrackFromResource(resourcePath);
            int height = layout.length;
            int width = layout[0].length();

            circuit = new Circuit(width, height);
            initializeTrack(layout);
            locateCarStartPosition();
        } catch (IOException e) {
            System.out.println("Error reading track file: " + e.getMessage());
        }
    }

    private String[] readTrackFromResource(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath.substring(1)); // Remove leading slash
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList()).toArray(new String[0]);
        }
    }



    private void initializeTrack(String[] layout) {
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[y].length(); x++) {
                char ch = layout[y].charAt(x);
                RaceCar carType = getRaceCarByChar(ch);
                CircuitField cell = new CircuitField(x, y, carType);
                circuit.addCells(cell);
            }
        }
    }

    private void locateCarStartPosition() {
        for (CircuitField field : circuit.getStartGridPosition()) {
            carX = field.getX();
            carY = field.getY();
            break; // Assume there's only one start position for simplicity
        }
    }

    private RaceCar getRaceCarByChar(char ch) {
        return switch (ch) {
            case '.' -> RaceCar.TRACK;       // Grey/clear
            case 'S' -> RaceCar.START;       // Green
            case '*' -> RaceCar.OUT_OF_TRACK; // Black
            case '#' -> RaceCar.FINISH;    // Red
            default -> throw new IllegalArgumentException("Unknown track character: " + ch);
        };
    }


    public void moveCar(int deltaX, int deltaY) {
        int newX = carX + deltaX;
        int newY = carY + deltaY;

        CircuitField targetCell = circuit.getCell(newX, newY);
        if (targetCell != null && targetCell.getCarType() != RaceCar.OUT_OF_TRACK) {
            carX = newX;
            carY = newY;
        } else {
            System.out.println("Invalid move! Car cannot move out-of-track.");
        }
    }

    public void printTrack() {
        if (circuit == null) {
            System.out.println("Track is not initialized.");
            return;
        }

        CircuitField[][] matrix = circuit.getGridAsMatrix();
        for (int y = 0; y < circuit.getHeight(); y++) {
            for (int x = 0; x < circuit.getWidth(); x++) {
                if (x == carX && y == carY) {
                    System.out.print('C'); // Represent the car with 'C'
                } else {
                    CircuitField cell = matrix[x][y];
                    System.out.print(cell != null ? cell.getCarType().getSymbol() : ' ');
                }
            }
            System.out.println();
        }
    }
}
