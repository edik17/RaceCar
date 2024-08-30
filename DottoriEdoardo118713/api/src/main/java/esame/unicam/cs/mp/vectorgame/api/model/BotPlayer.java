package esame.unicam.cs.mp.vectorgame.api.model;

import esame.unicam.cs.mp.vectorgame.api.model.game.Player;
import esame.unicam.cs.mp.vectorgame.api.model.game.RaceCar;
import esame.unicam.cs.mp.vectorgame.api.model.game.Track;

import java.util.List;
import java.util.Random;

public class BotPlayer extends Player<CircuitField> {
    private final Random random;

    public BotPlayer(String name, CircuitField startPosition, Track<CircuitField> track) {
        super(name, startPosition, track);
        this.random = new Random();
    }

    @Override
    public CircuitField adjacentMove() {
        List<CircuitField> neighbors = getCurrentPosition().getNeighbors();
        List<CircuitField> safeNeighbors = neighbors.stream()
                .filter(n -> n.getCarType().equals(RaceCar.TRACK) || n.getCarType().equals(RaceCar.FINISH))
                .toList();
        if (random.nextInt(20) == 0) return new CircuitField(-1, -1, RaceCar.OUT_OF_TRACK);

        return safeNeighbors.get(random.nextInt(safeNeighbors.size()));
    }

    @Override
    public CircuitField move() {
        if (getLastMove() == null) return adjacentMove();

        int x = getCurrentPosition().getX() + getLastMove().getDeltaX();
        int y = getCurrentPosition().getY() + getLastMove().getDeltaY();

        if (x < 0 || y < 0 || x >= getTrack().getWidth() || y >= getTrack().getHeight()) return adjacentMove();
        else
        {
            CircuitField next = getTrack().getCell(x, y);
            if (next.getCarType().equals(RaceCar.TRACK)) return next;
            else return adjacentMove();
        }
    }
}
