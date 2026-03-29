package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {

    private final List<List<Direction>> paths;

    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
        if(Side.CHO == side) {
            paths = List.of(
                    List.of(Direction.UP), List.of(Direction.RIGHT), List.of(Direction.LEFT)
            );
            return;
        }
        paths = List.of(
                List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
        );
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        List<Position> positions = new ArrayList<>();
        for(List<Direction> path : paths) {
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
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "졸";
        }
        return "병";
    }
}
