package domain;

import java.util.HashMap;
import java.util.Map;

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
        return 1 <= nx && nx <= 9 && 1 <= ny && ny <= 10;
    }

    public Position createPosition(int dx, int dy) {
        return Position.of(x + dx, y + dy);
    }

    private static void validateOutOfRange(int x, int y) {
        if(!((1 <= x && x <= 9) && (1 <= y && y <= 10))) {
            throw new IllegalArgumentException("x 좌표는 1~9, y 좌표는 1~10, 사이여야 합니다.");
        }
    }
}