package domain;

import domain.piece.PieceType;

import java.util.List;

public enum Formation {
    상마마상(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT)),
    마상상마(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    상마상마(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    마상마상(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT));

    private final List<PieceType> pieceTypes;

    Formation(List<PieceType> pieceTypes) {
        this.pieceTypes = pieceTypes;
    }

    public List<PieceType> getPieceTypes() {
        return pieceTypes;
    }
}
