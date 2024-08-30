package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Engine;
import esame.unicam.cs.mp.vectorgame.api.model.game.Grid;
import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;
import esame.unicam.cs.mp.vectorgame.api.utils.Print;

import java.util.List;
import java.util.Random;

public class RaceEngine<T extends Grid<T>> implements Engine<T> {

    private final List<Player<T>> players;
    private boolean isRunning;
    private final Random random;
    private Track<T> track;
    private List<Player<T>> listPlayers;

    public RaceEngine(List<Player<T>> players) {
        this.players = players;
        this.isRunning = false;
        this.random = new Random();
    }

    @Override
    public void play() {
        if(!isTerminated()){
            players.forEach(player -> {
                T newPosition = random.nextBoolean() ? player.move() : player.adjacentMove();
                player.setPosition(newPosition);
                Print.printPlayerPosition(player);
                if (player.hasFinished()) {
                    Print.printPlayerVictory(player);
                    isRunning = false;
                }
            });
            deletePlayers(players.stream().filter(Player::hasCrashed).toList());
        }
    }

    @Override
    public List<Player<T>> getPlayers() {
        return this.players;
    }

    @Override
    public boolean isTerminated() {
        return !isRunning || players.isEmpty();
    }

    private void deletePlayers(List<Player<T>> players) {
        this.players.removeAll(players);
        players.forEach(Print::printPlayerElimination);
    }

    public void setTrack(Track<T> track) {
        this.track = track;
    }

    public void setPlayers(List<Player<T>> players) {
        this.listPlayers = players;
    }
}
