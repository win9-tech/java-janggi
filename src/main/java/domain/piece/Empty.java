package domain.piece;

import domain.Position;
import domain.Side;

import java.util.List;

public class Empty extends Piece{

    private static final String PIECE_NOT_FOUND = "해당 위치에 기물이 존재하지 않습니다.";

    public Empty() {
        super(Side.NONE, null);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public void validateExists() {
        throw new IllegalArgumentException(PIECE_NOT_FOUND);
    }

    @Override
    public String getName() {
        return "．";
    }
}
