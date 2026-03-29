package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    @ParameterizedTest
    @CsvSource({
            "5, 2, 5, 3, HAN, true",
            "5, 2, 5, 1, HAN, true",
            "5, 2, 4, 2, HAN, true",
            "5, 2, 6, 2, HAN, true",

            "5, 2, 5, 4, HAN, false"
    })
    @DisplayName("궁이 이동할 수 있는 위치인지 검증한다.")
    void 기물이_이동할_수_있는_위치인지_검증한다(
            int sourceX, int sourceY, int targetX, int targetY, Side side, boolean pass
    ) {
        // given
        Position sourcePosition = Position.of(sourceX, sourceY);
        Position targetPosition = Position.of(targetX, targetY);
        Piece piece = new King(side);

        // when & then
        if (pass) {
            Assertions.assertThatCode(() -> piece.findRoute(sourcePosition, targetPosition))
                    .doesNotThrowAnyException();
        } else {
            Assertions.assertThatThrownBy(() -> piece.findRoute(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
