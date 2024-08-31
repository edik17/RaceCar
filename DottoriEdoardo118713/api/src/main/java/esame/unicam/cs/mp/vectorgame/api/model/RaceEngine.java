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

    /**
     * Runs the game loop, updating player positions and checking for end conditions.
     */
    @Override
    public void play() {
        while (isRunning) {
            for (Player<T> player : players) {
                if (!player.hasFinished() && !player.hasCrashed()) {
                    player.updatePosition();
                    if (player.hasFinished()) {
                        System.out.println("Player has won the race!");
                        isRunning = false;
                        break;
                    } else if (player.hasCrashed()) {
                        System.out.println("Player has crashed!");
                    }
                }
            }
        }
    }

    @Override
    public List<Player<T>> getPlayers() {
        return players;
    }

    @Override
    public boolean isTerminated() {
        return !isRunning || players.stream().allMatch(Player::hasFinished);
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
