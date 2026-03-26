package domain;

import domain.piece.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public Board(Formation choFormation, Formation hanFormation) {
        createBoard(choFormation, hanFormation);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    private void createBoard(Formation choFormation, Formation hanFormation) {
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 10; y++) {
                placePiece(Position.of(x, y), PieceType.EMPTY.create(Side.NONE));
            }
        }
        placePieces(choFormation, Side.CHO);
        placePieces(hanFormation, Side.HAN);
    }

    private void placePieces(Formation hanFormation, Side side) {

        List<Integer> rows = getRowForSide(side);

        placePiece(Position.of(1, rows.get(0)), PieceType.CHARIOT.create(side));
        placePiece(Position.of(9, rows.get(0)), PieceType.CHARIOT.create(side));

        placePiece(Position.of(4, rows.get(0)), PieceType.GUARD.create(side));
        placePiece(Position.of(6, rows.get(0)), PieceType.GUARD.create(side));

        placePiece(Position.of(5, rows.get(1)), PieceType.KING.create(side));

        placePiece(Position.of(2, rows.get(2)), PieceType.CANNON.create(side));
        placePiece(Position.of(8, rows.get(2)), PieceType.CANNON.create(side));

        placePiece(Position.of(1, rows.get(3)), PieceType.SOLDIER.create(side));
        placePiece(Position.of(3, rows.get(3)), PieceType.SOLDIER.create(side));
        placePiece(Position.of(5, rows.get(3)), PieceType.SOLDIER.create(side));
        placePiece(Position.of(7, rows.get(3)), PieceType.SOLDIER.create(side));
        placePiece(Position.of(9, rows.get(3)), PieceType.SOLDIER.create(side));

        placePiece(Position.of(2, rows.get(0)), hanFormation.getPieceTypes().get(0).create(side));
        placePiece(Position.of(3, rows.get(0)), hanFormation.getPieceTypes().get(1).create(side));
        placePiece(Position.of(7, rows.get(0)), hanFormation.getPieceTypes().get(2).create(side));
        placePiece(Position.of(8, rows.get(0)), hanFormation.getPieceTypes().get(3).create(side));
    }

    private List<Integer> getRowForSide(Side side) {
        if(side == Side.HAN) {
            return List.of(1,2,3,4);
        }
        return List.of(10,9,8,7);
    }

    private void placePiece(Position position, Piece piece) {
        board.put(position, piece);
    }
}
