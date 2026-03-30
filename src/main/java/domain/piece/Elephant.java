package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.PathMovement;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    private final List<List<Direction>> paths = List.of(
            List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT), List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT), List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT), List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT), List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT)
    );

    public Elephant(Side side) {
        super(side, new PathMovement());
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        for (List<Direction> path : paths) {
            List<Position> positions = new ArrayList<>();
            Position current = sourcePosition;
            for (Direction direction : path) {
                if (!current.canMove(direction.getX(), direction.getY())) {
                    break;
                }
                current = current.createPosition(direction.getX(), direction.getY());
                positions.add(current);
            }
            if (positions.size() == path.size() && current.equals(targetPosition)) {
                return positions;
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        return "상";
    }
}
