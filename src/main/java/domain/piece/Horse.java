package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    private final List<List<Direction>> movementStrategy = List.of(
      List.of(Direction.UP, Direction.UP_LEFT), List.of(Direction.UP, Direction.UP_RIGHT),
      List.of(Direction.RIGHT, Direction.UP_RIGHT), List.of(Direction.RIGHT, Direction.DOWN_RIGHT),
      List.of(Direction.DOWN, Direction.DOWN_LEFT), List.of(Direction.DOWN, Direction.DOWN_RIGHT),
      List.of(Direction.LEFT, Direction.UP_LEFT), List.of(Direction.LEFT, Direction.DOWN_LEFT)
    );

    public Horse(Side side) {
        super(side);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        List<Position> positions = new ArrayList<>();
        for(List<Direction> path : movementStrategy) {
            Position position = sourcePosition;
            for(Direction direction : path) {
                try{
                    position = position.createPosition(direction.getX(), direction.getY());
                    positions.add(position);
                } catch (IllegalArgumentException e) {
                    break;
                }
            }
            if(positions.contains(targetPosition)) {
                return List.copyOf(positions);
            }
            positions.clear();
        }
        throw new IllegalArgumentException("이동할 수 없는 목적지입니다.");
    }

    @Override
    public String getName() {
        return "마";
    }
}
