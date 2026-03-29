package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.List;

public abstract class MovementStrategy {

    protected final String ERROR_PREFIX = "[ERROR] ";
    protected final String INVALID_TARGET_POSITION = ERROR_PREFIX + "이동할 수 없는 목적지입니다.";


    public abstract List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition, Position targetPosition);

    protected abstract List<Position> buildRoute(List<Direction> route, Position sourcePosition, Position targetPosition);
}

