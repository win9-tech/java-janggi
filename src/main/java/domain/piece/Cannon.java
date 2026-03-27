package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    private final List<List<Direction>> movementStrategy = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Cannon(Side side) {
        super(side);
    }


    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        for (List<Direction> path : movementStrategy) {
            List<Position> positions = new ArrayList<>();
            Direction direction = path.getFirst();
            Position position = sourcePosition;

            while (true) {
                try {
                    Position nextPosition = position.createPosition(direction.getX(), direction.getY());
                    positions.add(nextPosition);
                    if (nextPosition.equals(targetPosition)) {
                        return positions;
                    }
                    position = nextPosition;
                } catch (IllegalArgumentException e) {
                    break;
                }
            }
        }
        throw new IllegalArgumentException("이동할 수 없는 목적지입니다.");
    }

    @Override
    public boolean checkRoute(List<Piece> pieces) {
        // 목적지까지는 포가아닌 기물이 한 개만 있어야 함.
        int cnt = 0;
        for(Piece piece : pieces) {
            if(piece instanceof Cannon) {
                throw new IllegalArgumentException("포를 넘어갈 수 없습니다.");
            }
            if(!(piece instanceof Empty)) {
                cnt++;
            }
        }
        if(cnt != 1) {
            throw new IllegalArgumentException("이동할 수 없는 목적지입니다.");
        }
        return true;
    }

    @Override
    public void checkTarget(Piece piece) {
        if(piece.getSide().equals(side)) {
            throw new IllegalArgumentException("아군 기물은 잡을 수 없습니다.");
        }

        if(piece instanceof Cannon) {
            throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다.");
        }
    }

    @Override
    public String getName() {
        return "포";
    }
}
