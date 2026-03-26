package domain.piece;

import domain.Side;

public enum PieceType {
    ELEPHANT{
        @Override
        public Piece create(Side side) {
            return new Elephant(side);
        }
    },
    CANNON{
        @Override
        public Piece create(Side side) {
            return new Cannon(side);
        }
    },
    CHARIOT{
        @Override
        public Piece create(Side side) {
            return new Chariot(side);
        }
    },
    GUARD{
        @Override
        public Piece create(Side side) {
            return new Guard(side);
        }
    },
    HORSE{
        @Override
        public Piece create(Side side) {
            return new Horse(side);
        }
    },
    KING{
        @Override
        public Piece create(Side side) {
            return new King(side);
        }
    },
    EMPTY{
        @Override
        public Piece create(Side side) {
            return new Empty();
        }
    },
    SOLDIER{
        @Override
        public Piece create(Side side) {
            return new Soldier(side);
        }
    };

    public abstract Piece create (Side side);
}
