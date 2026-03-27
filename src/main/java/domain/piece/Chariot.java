package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private final List<List<Direction>> movementStrategy = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side) {
        super(side);
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
        throw new IllegalArgumentException("이동할 수 없는 목적지입니다.");
    }

    @Override
    public String getName() {
        return "차";
    }
}
