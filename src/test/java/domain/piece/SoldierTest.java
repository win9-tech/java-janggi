package domain.piece;

import domain.Position;
import domain.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @ParameterizedTest
    @CsvSource({
            "1, 7, 1, 6, CHO, true",
            "1, 7, 1, 5, CHO, false",
            "1, 4, 1, 5, HAN, true",
            "1, 4, 1, 3, HAN, false"
    })
    @DisplayName("초/병이 이동할 수 있는 위치인지 검증한다.")
    void 기물이_이동할_수_있는_위치인지_검증한다(
            int sourceX, int sourceY, int targetX, int targetY, Side side, boolean pass
    ) {
        // given
        Position sourcePosition = Position.of(sourceX, sourceY);
        Position targetPosition = Position.of(targetX, targetY);
        Piece piece = new Soldier(side);

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
