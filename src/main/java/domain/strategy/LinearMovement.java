package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class LinearMovement extends MovementStrategy {

    @Override
    public List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition, Position targetPosition) {
        for(List<Direction> path: paths) {
            List<Position> positions = buildRoute(path, sourcePosition, targetPosition);
            if(positions.contains(targetPosition)) {
                return List.copyOf(positions);
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    protected List<Position> buildRoute(List<Direction> path, Position sourcePosition, Position targetPosition) {
        List<Position> route = new ArrayList<>();
        Direction direction = path.getFirst();
        Position position = sourcePosition;
        while (true) {
            try {
                Position nextPosition = position.createPosition(direction.getX(), direction.getY());
                route.add(nextPosition);
                if (nextPosition.equals(targetPosition)) {
                    return List.copyOf(route);
                }
                position = nextPosition;
            } catch (IllegalArgumentException e) {
                route.clear();
                break;
            }
        }
        return List.copyOf(route);
    }
}
