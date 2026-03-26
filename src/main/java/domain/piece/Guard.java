package domain.piece;

import domain.Side;

public class Guard extends Piece {
    public Guard(Side side) {
        super(side);
    }

    @Override
    public String getName() {
        return "사";
    }
}
