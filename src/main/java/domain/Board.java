package domain;

import domain.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public Board(Formation choFormation, Formation hanFormation) {
        createBoard(choFormation, hanFormation);
    }

    public void movePiece(Side side, Position sourcePosition, Position targetPosition) {
        // 보드 출발지에서 기물을 꺼낸다.
        Piece piece = board.get(sourcePosition);
        // Empty인지 아닌지 확인한다.
        if(piece instanceof Empty) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
        // 팀인지 확인한다.
        if(!piece.getSide().equals(side)) {
            throw new IllegalArgumentException("선택한 기물은 아군 기물이 아닙니다.");
        }

        // 기물에게 경로를 묻는다.
        List<Position> route = piece.findRoute(sourcePosition, targetPosition);
        List<Piece> pieces = new ArrayList<>();
        for(Position position : route) {
            if(!position.equals(targetPosition)) {
                pieces.add(board.get(position));
            }
        }

        // 경로에 있는 기물을 확인하여 이동 가능한 경로인지 확인한다
        piece.checkRoute(pieces);

        piece.checkTarget(board.get(targetPosition));
        // 목적지로 보내고 출발지를 Empty로 채운다.
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
