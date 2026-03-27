package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    private final List<List<Direction>> movementStrategy;

    public Soldier(Side side) {
        super(side);
        if(Side.CHO == side) {
            movementStrategy = List.of(
                    List.of(Direction.UP), List.of(Direction.RIGHT), List.of(Direction.LEFT)
            );
            return;
        }
        movementStrategy = List.of(
                List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
        );
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
        if (isSameSide(Side.CHO)) {
            return "졸";
        }
        return "병";
    }
}
