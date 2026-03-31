package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final List<List<Direction>> paths = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        for (List<Direction> path : paths) {
            List<Position> positions = new ArrayList<>();
            Direction direction = path.getFirst();
            Position current = sourcePosition;

            while (current.canMove(direction.getX(), direction.getY())) {
                current = current.createPosition(direction.getX(), direction.getY());
                positions.add(current);
                if (current.equals(targetPosition)) {
                    return positions;
                }
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        return "차";
    }
}
