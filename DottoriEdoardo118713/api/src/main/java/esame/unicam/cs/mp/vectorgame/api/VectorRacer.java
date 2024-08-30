package esame.unicam.cs.mp.vectorgame.api;

import java.util.Scanner;

public class VectorRacer {
    public static void main(String[] args) {
        TrackController trackController = new TrackController();
        trackController.createTrackFromFile("TrackApi.txt");

        Scanner scanner = new Scanner(System.in);

        String command;
        do {
            trackController.printTrack();
            System.out.print("Enter move (WASD) or Q to quit: ");

            if (scanner.hasNextLine()) {
                command = scanner.nextLine().toUpperCase();
            } else {
                System.out.println("No input found.");
                break;
            }

            switch (command) {
                case "W" -> trackController.moveCar(0, -1); // Move up
                case "A" -> trackController.moveCar(-1, 0); // Move left
                case "S" -> trackController.moveCar(0, 1); // Move down
                case "D" -> trackController.moveCar(1, 0); // Move right
                case "Q" -> System.out.println("Quitting the game.");
                default -> System.out.println("Invalid command! Use W, A, S, D to move or Q to quit.");
            }
        } while (!command.equals("Q"));
    }

}
