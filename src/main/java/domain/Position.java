package domain;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        validateOutOfRange(x, y);
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x) * 31 + Objects.hashCode(y);
    }

    private static void validateOutOfRange(int x, int y) {
        if(!((1 <= x && x <= 9) && (1 <= y && y <= 10))) {
            throw new IllegalArgumentException("y 좌표는 1~10, x 좌표는 1~9 사이여야 합니다.");
        }
    }

    public Position createPosition(int dx, int dy) {
        return Position.of(x + dx, y + dy);
    }
}