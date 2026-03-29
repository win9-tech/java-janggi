package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private final List<List<Direction>> movementStrategy = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        for (List<Direction> path : movementStrategy) {
            List<Position> positions = new ArrayList<>();
            Direction direction = path.getFirst();
            Position position = sourcePosition;

            while (true) {
                try {
                    Position nextPosition = position.createPosition(direction.getX(), direction.getY());
                    positions.add(nextPosition);
                    if (nextPosition.equals(targetPosition)) {
                        return positions;
                    }
                    position = nextPosition;
                } catch (IllegalArgumentException e) {
                    break;
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
