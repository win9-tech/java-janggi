package domain.piece;

import domain.Position;
import domain.Side;

import java.util.List;

import static constant.ErrorMessage.INVALID_TARGET_POSITION;

public class Empty extends Piece{

    public Empty() {
        super(Side.NONE, null);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public String getName() {
        return "．";
    }
}
