package domain.piece;

import domain.Side;

public class King extends Piece {
    public King(Side side) {
        super(side);
    }

    @Override
    public String getName() {
        return "왕";
    }
}
