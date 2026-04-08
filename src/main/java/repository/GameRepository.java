package repository;

import domain.Position;
import domain.Turn;
import domain.piece.Piece;

import java.util.Map;
import java.util.Optional;

public interface GameRepository {

    Long getNextId();

    void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board);

    void deleteBoard(Long gameId);

    Optional<GameStatus> findBoard(String gameId);
}
