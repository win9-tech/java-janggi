package repository;

import domain.BoardStatus;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;

import java.util.Map;

public interface JdbcRepository {

    Long getNextId();

    void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board);

    BoardStatus findBoard(String gameId);
}
