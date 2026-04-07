package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

public class Chariot extends Piece {

    private static final List<List<Direction>> paths = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition) {
        return movementStrategy.findRoute(paths, sourcePosition);
    }

    @Override
    public List<Position> findPathTo(Position source, Position target) {
        return movementStrategy.findPathTo(paths, source, target);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "车";
        }
        return "車";
    }
}
