import domain.*;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @DisplayName("초나라 포진을 상마마상으로 배치한다.")
    @Test
    void 초나라_포진을_상마마상으로_배치한다() {
        Board board = new Board(Formation.from("1"), Formation.from("2"));
        Map<Position, Piece> pieces = board.getBoard();

        assertThat(pieces.get(Position.of(2, 10))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(Position.of(3, 10))).isInstanceOf(Horse.class);
        assertThat(pieces.get(Position.of(7, 10))).isInstanceOf(Horse.class);
        assertThat(pieces.get(Position.of(8, 10))).isInstanceOf(Elephant.class);
    }

    @DisplayName("한나라 포진을 마상마상으로 배치한다.")
    @Test
    void 한나라_포진을_마상마상으로_배치한다() {
        Board board = new Board(Formation.from("1"), Formation.from("4"));
        Map<Position, Piece> pieces = board.getBoard();

        assertThat(pieces.get(Position.of(2, 1))).isInstanceOf(Horse.class);
        assertThat(pieces.get(Position.of(3, 1))).isInstanceOf(Elephant.class);
        assertThat(pieces.get(Position.of(7, 1))).isInstanceOf(Horse.class);
        assertThat(pieces.get(Position.of(8, 1))).isInstanceOf(Elephant.class);
    }

    @DisplayName("각 궁이 올바른 진영에 배치된다.")
    @Test
    void 각_궁이_올바른_진영에_배치된다() {
        Board board = new Board(Formation.from("1"), Formation.from("2"));
        Map<Position, Piece> pieces = board.getBoard();

        assertThat(pieces.get(Position.of(5, 2)).isSameSide(Side.HAN)).isTrue();
        assertThat(pieces.get(Position.of(5, 9)).isSameSide(Side.CHO)).isTrue();
    }
}
