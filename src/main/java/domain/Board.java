package domain;

import domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public Board(Formation choFormation, Formation hanFormation) {
        createBoard(choFormation, hanFormation);
    }

    private void createBoard(Formation choFormation, Formation hanFormation) {
        for(int x = 1; x<= 9; x++) {
            for(int y = 1; y <= 10; y++) {
                createPiece(Position.of(x, y), new Empty());
            }
        }

        createPiece(Position.of(1, 1), new Chariot(Side.HAN));
        createPiece(Position.of(1, 9), new Chariot(Side.HAN));
        createPiece(Position.of(1, 10), new Chariot(Side.CHO));
        createPiece(Position.of(9, 10), new Chariot(Side.CHO));

        createPiece(Position.of(3, 2), new Cannon(Side.HAN));
        createPiece(Position.of(3, 8), new Cannon(Side.HAN));
        createPiece(Position.of(8, 2), new Cannon(Side.CHO));
        createPiece(Position.of(8, 8), new Cannon(Side.CHO));

        createPiece(Position.of(1, 4), new Guard(Side.HAN));
        createPiece(Position.of(1, 6), new Guard(Side.HAN));
        createPiece(Position.of(4, 10), new Guard(Side.CHO));
        createPiece(Position.of(6, 10), new Guard(Side.CHO));

        createPiece(Position.of(2, 5), new King(Side.HAN));
        createPiece(Position.of(9, 5), new King(Side.CHO));

        createPiece(Position.of(4, 1), new Soldier(Side.HAN));
        createPiece(Position.of(4, 3), new Soldier(Side.HAN));
        createPiece(Position.of(4, 5), new Soldier(Side.HAN));
        createPiece(Position.of(4, 7), new Soldier(Side.HAN));
        createPiece(Position.of(4, 9), new Soldier(Side.HAN));
        createPiece(Position.of(7, 1), new Soldier(Side.CHO));
        createPiece(Position.of(7, 3), new Soldier(Side.CHO));
        createPiece(Position.of(7, 5), new Soldier(Side.CHO));
        createPiece(Position.of(7, 7), new Soldier(Side.CHO));
        createPiece(Position.of(7, 9), new Soldier(Side.CHO));

        if(choFormation == Formation.상마마상) {
            createPiece(Position.of(2, 10), new Elephant(Side.CHO));    // 마
            createPiece(Position.of(3, 10), new Horse(Side.CHO)); // 상
            createPiece(Position.of(7, 10), new Horse(Side.CHO)); // 상
            createPiece(Position.of(8, 10), new Elephant(Side.CHO));    // 마
        } else if(choFormation == Formation.마상상마) {
            createPiece(Position.of(2, 10), new Horse(Side.CHO));
            createPiece(Position.of(3, 10), new Elephant(Side.CHO));
            createPiece(Position.of(7, 10), new Elephant(Side.CHO));
            createPiece(Position.of(8, 10), new Horse(Side.CHO));
        } else if(choFormation == Formation.상마상마) {
            createPiece(Position.of(2, 10), new Elephant(Side.CHO));
            createPiece(Position.of(3, 10), new Horse(Side.CHO));
            createPiece(Position.of(7, 10), new Elephant(Side.CHO));
            createPiece(Position.of(8, 10), new Horse(Side.CHO));
        } else if(choFormation == Formation.마상마상) {
            createPiece(Position.of(2, 10), new Horse(Side.CHO));
            createPiece(Position.of(3, 10), new Elephant(Side.CHO));
            createPiece(Position.of(7, 10), new Horse(Side.CHO));
            createPiece(Position.of(8, 10), new Elephant(Side.CHO));
        }

        if(hanFormation == Formation.상마마상) {
            createPiece(Position.of(2, 1), new Elephant(Side.HAN));
            createPiece(Position.of(3, 1), new Horse(Side.HAN));     // 마
            createPiece(Position.of(7, 1), new Horse(Side.HAN));     // 마
            createPiece(Position.of(8, 1), new Elephant(Side.HAN));  // 상
        } else if(hanFormation == Formation.마상상마) {
            createPiece(Position.of(2, 1), new Horse(Side.HAN));  // 상
            createPiece(Position.of(3, 1), new Elephant(Side.HAN));     // 마
            createPiece(Position.of(7, 1), new Elephant(Side.HAN));     // 마
            createPiece(Position.of(8, 1), new Horse(Side.HAN));  // 상
        } else if(hanFormation == Formation.상마상마) {
            createPiece(Position.of(2, 1), new Elephant(Side.HAN));  // 상
            createPiece(Position.of(3, 1), new Horse(Side.HAN));     // 마
            createPiece(Position.of(7, 1), new Elephant(Side.HAN));     // 마
            createPiece(Position.of(8, 1), new Horse(Side.HAN));  // 상
        } else if(hanFormation == Formation.마상마상) {
            createPiece(Position.of(2, 1), new Horse(Side.HAN));  // 상
            createPiece(Position.of(3, 1), new Elephant(Side.HAN));     // 마
            createPiece(Position.of(7, 1), new Horse(Side.HAN));     // 마
            createPiece(Position.of(8, 1), new Elephant(Side.HAN));  // 상
        }
    }

    private void createPiece(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
