package esame.unicam.cs.mp.vectorgame.app;

import esame.unicam.cs.mp.vectorgame.api.model.BotPlayer;
import esame.unicam.cs.mp.vectorgame.api.model.CircuitField;
import esame.unicam.cs.mp.vectorgame.api.model.RaceEngine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Engine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import esame.unicam.cs.mp.vectorgame.api.utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the initialization and control of the Formula 1 simulation game.
 * This controller sets up the game environment, including the track and players, and handles game progression.
 */
public class GameController {

    private RaceEngine<CircuitField> gameEngine;
    private Track<CircuitField> track;

    /**
     * Returns the game engine associated with the game.
     *
     * @return the current game engine instance.
     */
    public Engine<CircuitField> getGameEngine() {
        return gameEngine;
    }

    /**
     * Retrieves the list of players participating in the game from the game engine.
     *
     * @return a list of players.
     */
    public List<Player<CircuitField>> getPlayers() {
        return this.gameEngine.getPlayers();
    }

    /**
     * Gets the track currently being used in the game.
     *
     * @return the track instance.
     */
    public Track<CircuitField> getTrack() {
        return track;
    }

    /**
     * Initializes the game by parsing the track configuration, setting up the track,
     * and initializing bots as players based on the start positions defined in the track.
     *
     * @throws IOException if there is an issue reading the track configuration file.
     */
    public void initialize() throws IOException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("Track.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Parser parser = new Parser();
            this.track = parser.parseFile(reader);
            List<Player<CircuitField>> players = new ArrayList<>();
            initializeBots(players, track, track.getStartGridPosition().size());
            this.gameEngine = new RaceEngine<>(players);
        } catch (IOException e) {
            throw new IOException("Failed to initialize the game due to an input/output error.", e);
        }
    }

    /**
     * Initializes bot players and assigns them to start positions on the track.
     *
     * @param players the list of players to be filled with newly created bots.
     * @param track the track from which start positions are taken.
     * @param numBots the number of bots to initialize, typically equal to the number of start positions.
     */
    private static void initializeBots(List<Player<CircuitField>> players, Track<CircuitField> track, int numBots) {
        List<CircuitField> startPositions = track.getStartGridPosition();
        for (int i = 0; i < numBots && i < startPositions.size(); i++) {
            BotPlayer bot = new BotPlayer(startPositions.get(i), track);
            players.add(bot);
        }
    }
}