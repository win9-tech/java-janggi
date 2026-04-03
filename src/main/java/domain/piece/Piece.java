package domain.piece;

import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

import static constant.ErrorMessage.CANNOT_CAPTURE_OWN_PIECE;
import static constant.ErrorMessage.ROUTE_BLOCKED;

public abstract class Piece {

    protected final Side side;
    protected final MovementStrategy movementStrategy;

    protected Piece(Side side, MovementStrategy movementStrategy) {
        this.side = side;
        this.movementStrategy = movementStrategy;
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public Side getSide() {
        return side;
    }

    public void checkTarget(Piece piece) {
        if(side.equals(piece.side)) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
        }
    }

    public void checkRoute(List<Piece> pieces) {
        for(Piece piece : pieces) {
            if(!(piece.isEmpty())) {
                throw new IllegalArgumentException(ROUTE_BLOCKED);
            }
        }
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isCannon() {
        return false;
    }

    public abstract List<Position> findRoute(Position sourcePosition, Position targetPosition);

    public abstract String getName();
}
