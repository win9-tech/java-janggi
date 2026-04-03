package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.List;

import static constant.ErrorMessage.INVALID_TARGET_POSITION;

public abstract class MovementStrategy {

    public List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition, Position targetPosition) {
        for(List<Direction> path: paths) {
            List<Position> positions = buildRoute(path, sourcePosition, targetPosition);
            if(!positions.isEmpty() && positions.getLast().equals(targetPosition)) {
                return positions;
            }
        }
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    protected abstract List<Position> buildRoute(List<Direction> route, Position sourcePosition, Position targetPosition);
}

