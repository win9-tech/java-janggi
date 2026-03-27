package domain.piece;

import domain.Position;
import domain.Side;

import java.util.List;

public abstract class Piece {

    protected final Side side;

    protected Piece(Side side) {
        this.side = side;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public Side getSide() {
        return side;
    }

    public void checkTarget(Piece piece) {
        if(side.equals(piece.side)) {
            throw new IllegalArgumentException("아군 기물은 잡을 수 없습니다.");
        }
    }

    public abstract List<Position> findRoute(Position sourcePosition, Position targetPosition);

    public boolean checkRoute(List<Piece> pieces) {
        for(Piece piece : pieces) {
            if(!(piece instanceof Empty)) {
                return false;
            }
        }
        return true;
    }

    public abstract String getName();
}
