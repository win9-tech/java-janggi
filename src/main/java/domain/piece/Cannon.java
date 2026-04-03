package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

import static constant.ErrorMessage.*;

public class Cannon extends Piece {

    private static final List<List<Direction>> paths = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT));

    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movementStrategy.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public void checkRoute(List<Piece> pieces) {
        int jumpedPieceCount = 0;
        for(Piece piece : pieces) {
            if(piece.isCannon()) {
                throw new IllegalArgumentException(CANNOT_JUMP_WITH_CANNON);
            }
            if(!(piece.isEmpty())) {
                jumpedPieceCount++;
            }
        }
        if(jumpedPieceCount != 1) {
            throw new IllegalArgumentException(MUST_JUMP_EXACTLY_ONE);
        }
    }

    @Override
    public void checkTarget(Piece piece) {
        super.checkTarget(piece);
        if(piece.isCannon()) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_CANNON_WITH_CANNON);
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }


    @Override
    public String getName() {
        return "포";
    }
}
