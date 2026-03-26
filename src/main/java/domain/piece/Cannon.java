package domain.piece;

import domain.Side;

public class Cannon extends Piece {
    public Cannon(Side side) {
        super(side);
    }

    @Override
    public String getName() {
        return "포";
    }
}
