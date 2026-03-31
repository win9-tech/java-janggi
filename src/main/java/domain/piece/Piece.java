package domain.piece;

import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

public abstract class Piece {

    protected static final String CANNOT_CAPTURE_OWN_PIECE = "아군 기물은 잡을 수 없습니다.";
    protected static final String INVALID_TARGET_POSITION = "이동할 수 없는 목적지입니다.";

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
                throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
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
