package domain;

import domain.piece.Piece;

public class ScoreStatus {

    private double choScore = 0;
    private double hanScore = 1.5;

    public void reflect(Piece piece) {
        if (piece.getSide() == Side.CHO) {
            choScore += piece.getScore();
            return;
        }
        hanScore += piece.getScore();
    }

    public double getChoScore() {
        return choScore;
    }

    public double getHanScore() {
        return hanScore;
    }
}
