package domain;

public enum Side {
    CHO,
    HAN,
    NONE;

    public Side opposite() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
