package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @ParameterizedTest
    @CsvSource({
            "2, 3, 2, 7, HAN, true",
            "8, 3, 2, 3, HAN, true",
            "2, 3, 4, 6, HAN, false",
            "8, 3, 5, 9, HAN, false"
    })
    @DisplayName("기물이 이동할 수 있는 위치인지 검증한다.")
    void 기물이_이동할_수_있는_위치인지_검증한다(
            int sourceX, int sourceY, int targetX, int targetY, Side side, boolean expected
    ) {
        // given
        Position sourcePosition = Position.of(sourceX, sourceY);
        Position targetPosition = Position.of(targetX, targetY);
        Piece piece = PieceType.CHARIOT.create(Side.CHO);

        // when & then
        if (expected) {
            Assertions.assertThatCode(() -> piece.findRoute(sourcePosition, targetPosition))
                    .doesNotThrowAnyException();
        } else {
            Assertions.assertThatThrownBy(() -> piece.findRoute(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
