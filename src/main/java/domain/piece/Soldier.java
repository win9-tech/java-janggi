package domain.piece;

import domain.Side;

public class Soldier extends Piece {
    public Soldier(Side side) {
        super(side);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "졸";
        }
        return "병";
    }
}
