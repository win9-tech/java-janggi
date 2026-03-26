package domain.piece;

import domain.Side;

public abstract class Piece {

    private final Side side;

    protected Piece(Side side) {
        this.side = side;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }
}
