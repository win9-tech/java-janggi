package domain.piece;

import domain.Position;
import domain.Side;

import java.util.List;

public class Empty extends Piece{

    public Empty() {
        super(Side.NONE);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return List.of();
    }

    @Override
    public String getName() {
        return "．";
    }
}
