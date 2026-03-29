package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class PathMovement extends MovementStrategy{

    @Override
    public List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition, Position targetPosition) {
        for (List<Direction> path : paths) {
            List<Position> route = buildRoute(path, sourcePosition, targetPosition);
            if(route.contains(targetPosition)){
                return List.copyOf(route);
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    protected List<Position> buildRoute(List<Direction> path, Position sourcePosition, Position targetPosition) {
        List<Position> route = new ArrayList<>();
        Position position = sourcePosition;
        for(Direction direction : path) {
            try{
                position = position.createPosition(direction.getX(), direction.getY());
                route.add(position);
            } catch (IllegalArgumentException e) {
                route.clear();
                break;
            }
        }
        return route;
    }
}
