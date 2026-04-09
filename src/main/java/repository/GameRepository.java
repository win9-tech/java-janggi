package repository;

import domain.Game;

import java.util.Optional;

public interface GameRepository {

    Long getNextId();

    void saveGame(Game game);

    void deleteBoard(Long gameId);

    Optional<GameStatus> findBoard(String gameId);
}
