package domain;

import domain.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final int BACK_Y = 0;
    private final int KING_Y = 1;
    private final int CANNON_Y = 2;
    private final int SOLDIER_Y = 3;

    private final List<Integer> CANNON_X = List.of(2, 8);
    private final List<Integer> CHARIOT_X = List.of(1, 9);
    private final List<Integer> GUARD_X = List.of(4, 6);
    private final List<Integer> KING_X = List.of(5);
    private final List<Integer> SOLIDER_X = List.of(1, 3, 5, 7, 9);
    private final List<Integer> FORMATION_X = List.of(2, 3, 7, 8);

    private final Map<Position, Piece> board = new HashMap<>();

    public Board(Formation choFormation, Formation hanFormation) {
        createBoard(choFormation, hanFormation);
    }

    public void movePiece(Turn turn, Position sourcePosition, Position targetPosition) {
        Piece piece = board.get(sourcePosition);

        validatePieceExists(piece);
        validateOwnPiece(piece, turn);

        List<Position> route = piece.findRoute(sourcePosition, targetPosition);
        findPiecesOnRoute(piece, route, targetPosition);

        validateAvailableTarget(piece, targetPosition);
        board.put(targetPosition, board.get(sourcePosition));
        board.put(sourcePosition, new Empty());
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

    private void placePieces(Formation formation, Side side) {
        List<Integer> rows = getRowForSide(side);
        placePiece(CANNON_X, rows.get(CANNON_Y), PieceType.CANNON, side);
        placePiece(CHARIOT_X, rows.get(BACK_Y), PieceType.CHARIOT, side);
        placePiece(GUARD_X, rows.get(BACK_Y), PieceType.GUARD, side);
        placePiece(KING_X, rows.get(KING_Y), PieceType.KING, side);
        placePiece(SOLIDER_X, rows.get(SOLDIER_Y), PieceType.SOLDIER, side);
        placeFormationPiece(formation, FORMATION_X, rows.get(BACK_Y), side);
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

    private void placePiece(List<Integer> xPositions, int y, PieceType pieceType, Side side) {
        for(int x : xPositions) {
            board.put(Position.of(x, y), pieceType.create(side));
        }
    }

    private void placeFormationPiece(Formation formation, List<Integer> formationX, int y, Side side) {
        List<PieceType> pieceTypes = formation.getPieceTypes();
        for(int i = 0; i < pieceTypes.size(); i++) {
            board.put(Position.of(formationX.get(i), y), pieceTypes.get(i).create(side));
        }
    }

    private void validatePieceExists(Piece piece) {
        if(piece instanceof Empty) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void findPiecesOnRoute(Piece piece, List<Position> route, Position targetPosition) {
        List<Piece> pieces = new ArrayList<>();
        for(Position position : route) {
            if(!position.equals(targetPosition)) {
                pieces.add(board.get(position));
            }
        }
        piece.checkRoute(pieces);
    }

    private void validateOwnPiece(Piece piece, Turn turn) {
        if(!piece.getSide().equals(turn.current())) {
            throw new IllegalArgumentException("선택한 기물은 아군 기물이 아닙니다.");
        }
    }

    private void validateAvailableTarget(Piece piece, Position targetPosition) {
        piece.checkTarget(board.get(targetPosition));
    }
}
