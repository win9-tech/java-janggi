package repository;

import domain.GameStatus;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;

import java.util.Map;

public interface JdbcRepository {

    Long getNextId();

    void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board);

    GameStatus findBoard(String gameId);
}
