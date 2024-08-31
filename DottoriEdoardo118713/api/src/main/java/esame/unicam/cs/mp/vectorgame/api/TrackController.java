package esame.unicam.cs.mp.vectorgame.api;

import esame.unicam.cs.mp.vectorgame.api.model.Circuit;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * The TrackController class manages the game track creation, initialization, and player positioning.
 */
public class TrackController {
    private Circuit circuit;
    private int carX;
    private int carY;
    private int velocityX;
    private int velocityY;


    /**
     * Creates the track from a file resource. It reads the track layout from the specified
     * resource file path and initializes the Circuit object.
     *
     * @param resourcePath the path to the resource file containing the track layout
     */
    public void createTrackFromFile(String resourcePath) {
        try {
            String[] layout = readTrackFromResource(resourcePath);
            int height = layout.length;
            int width = layout[0].length();

            circuit = new Circuit(width, height);
            initializeTrack(layout);
        } catch (IOException e) {
            System.out.println("Error reading track file: " + e.getMessage());
        }
    }

    private String[] readTrackFromResource(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath.substring(1));
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

    private RaceCar getRaceCarByChar(char ch) {
        return switch (ch) {
            case '.' -> RaceCar.TRACK;        // Grey/clear
            case 'S' -> RaceCar.START;        // Green
            case '*' -> RaceCar.OUT_OF_TRACK; // Black
            case '#' -> RaceCar.FINISH;       // Red
            default -> throw new IllegalArgumentException("Unknown track character: " + ch);
        };
    }

    public Circuit getCircuit() {
        return circuit;
    }

    /**
     * Moves the car based on the change in direction. This method updates the car's position
     * and velocity and checks for collisions with the track boundaries.
     *
     * @param deltaX change in the X direction
     * @param deltaY change in the Y direction
     */
    public void moveCar(int deltaX, int deltaY) {
        // Adjust velocity based on input
         velocityX = deltaX;
        velocityY += deltaY;

        // Calculate new position based on velocity
        int newX = carX + velocityX;
        int newY = carY + velocityY;

        CircuitField targetCell = circuit.getCell(newX, newY);
        if (targetCell != null && targetCell.getCarType() != RaceCar.OUT_OF_TRACK) {
            // Move to new position
            carX = newX;
            carY = newY;

            if (targetCell.getCarType() == RaceCar.FINISH) {
                System.out.println("Congratulations! You've reached the finish line!");
            }
        } else {
            // Collision or out of track
            System.out.println("Invalid move! Car cannot move out-of-track or collision detected.");
            // Optionally reset the car's velocity to zero on invalid move
            velocityX = 0;
            velocityY = 0;
        }
    }

    /**
     * Prints the current state of the track, including the car's position.
     */
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
