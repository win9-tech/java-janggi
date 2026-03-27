package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {

    private final List<List<Direction>> movementStrategy = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Guard(Side side) {
        super(side);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        List<Position> positions = new ArrayList<>();
        for(List<Direction> path : movementStrategy) {
            for(Direction direction : path) {
                try{
                    positions.add(sourcePosition.createPosition(direction.getX(), direction.getY()));
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
        return "사";
    }
}
