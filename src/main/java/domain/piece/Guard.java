package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

public class Guard extends Piece {

    private final List<List<Direction>> paths = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        for(List<Direction> path : paths) {
            for(Direction direction : path) {
                if (!sourcePosition.canMove(direction.getX(), direction.getY())) {
                    continue;
                }
                Position next = sourcePosition.createPosition(direction.getX(), direction.getY());
                if (next.equals(targetPosition)) {
                    return List.of(next);
                }
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        return "사";
    }
}
