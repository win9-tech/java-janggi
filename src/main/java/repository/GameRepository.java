package repository;

import domain.GameStatus;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;

import java.util.Map;

public interface GameRepository {

    Long getNextId();

    void saveBoard(Long gameId, Turn turn, Map<Position, Piece> board);

    void deleteBoard(Long gameId);

    GameStatus findBoard(String gameId);
}
