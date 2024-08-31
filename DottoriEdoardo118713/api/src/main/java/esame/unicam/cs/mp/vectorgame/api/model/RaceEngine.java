package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Engine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.List;

/**
 * RaceEngine class simulates the game loop and manages the game state for each turn.
 *
 * @param <T> the specific type of grid used in the game
 */
public class RaceEngine<T extends Grid<T>> implements Engine<T> {
    private List<Player<T>> players;
    private boolean isRunning;
    private Track<T> track;

    public RaceEngine(List<Player<T>> players) {
        this.players = players;
        this.isRunning = true;
    }

    @Override
    public List<Player<T>> getPlayers() {
        return players;
    }


    public void setTrack(Track<T> track) {
        this.track = track;
        players.forEach(player -> player.setPosition(track.getStartGridPosition().get(0))); // Assumes all players start from the same position
    }

    public void setPlayers(List<Player<T>> players) {
        this.players = players;
        players.forEach(player -> player.setPosition(track.getStartGridPosition().get(0)));
    }
}
