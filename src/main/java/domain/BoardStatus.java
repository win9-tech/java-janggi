package domain;

import domain.piece.Piece;

import java.util.Map;

public class BoardStatus {

    private final Long gameId;
    private final Map<Position, Piece> board;
    private final Turn turn;

    private BoardStatus(Long gameId, Map<Position, Piece> board, Turn turn) {
        this.gameId = gameId;
        this.board = board;
        this.turn = turn;
    }

    public static BoardStatus of(Long gameId, Map<Position, Piece> board, Turn turn) {
        return new BoardStatus(gameId, board, turn);
    }

    public Long getGameId() {
        return gameId;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}
