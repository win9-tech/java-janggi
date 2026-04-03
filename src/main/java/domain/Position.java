package domain;

import java.util.HashMap;
import java.util.Map;

import static constant.BoardConstant.*;
import static constant.ErrorMessage.INVALID_POSITION_RANGE;

public class Position {

    private static final Map<Integer, Position> CACHE = new HashMap<>();
    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        validateOutOfRange(x, y);
        return CACHE.computeIfAbsent(x * 100 + y, k -> new Position(x, y));
    }

    public boolean canMove(int dx, int dy) {
        int nx = x + dx;
        int ny = y + dy;
        return MIN_X <= nx && nx <= MAX_X && MIN_Y <= ny && ny <= MAX_Y;
    }

    public Position createPosition(int dx, int dy) {
        return Position.of(x + dx, y + dy);
    }

    private static void validateOutOfRange(int x, int y) {
        if(!((MIN_X <= x && x <= MAX_X) && (MIN_Y <= y && y <= MAX_Y))) {
            throw new IllegalArgumentException(INVALID_POSITION_RANGE);
        }
    }
}