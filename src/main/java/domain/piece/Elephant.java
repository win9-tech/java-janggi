package domain.piece;

import domain.Side;

public class Elephant extends Piece {
    public Elephant(Side side) {
        super(side);
    }

    @Override
    public String getName() {
        return "상";
    }
}
