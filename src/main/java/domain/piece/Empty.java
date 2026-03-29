package domain.piece;

import domain.Position;
import domain.Side;

import java.util.List;

public class Empty extends Piece{

    public Empty() {
        super(Side.NONE, null);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        return "．";
    }
}
