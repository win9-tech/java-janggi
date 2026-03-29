import domain.*;
import domain.piece.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Nested
    @DisplayName("장기판 초기화")
    class initBoard {

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

    @DisplayName("초 턴에 한 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 초_턴에_한_기물을_선택한_경우_이동할_수_없다() {
        // given
        Board board = new Board(Formation.from("1"), Formation.from("1"));
        Turn turn = new Turn(Side.CHO);
        Position sourcePosition = Position.of(1, 4);
        Position mockPosition = Position.of(1, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> board.movePiece(turn, sourcePosition, mockPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");

    }


    @DisplayName("한 턴에 초 기물을 선택한 경우 이동할 수 없다.")
    @Test
    void 한_턴에_초_기물을_선택한_경우_이동할_수_없다() {
        // given
        Board board = new Board(Formation.from("1"), Formation.from("1"));
        Turn turn = new Turn(Side.HAN);
        Position sourcePosition = Position.of(1, 7);
        Position mockPosition = Position.of(1, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> board.movePiece(turn, sourcePosition, mockPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 기물은 아군 기물이 아닙니다.");

    }

    @DisplayName("출발지에 기물이 존재하지 않는 경우 이동할 수 없다.")
    @Test
    void 기물이_존재하지_않는_경우_이동할_수_없다() {
        // given
        Board board = new Board(Formation.from("1"), Formation.from("1"));
        Turn turn = new Turn(Side.CHO);
        Position sourcePosition = Position.of(5, 5);
        Position mockPosition = Position.of(1, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> board.movePiece(turn, sourcePosition, mockPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @Nested
    @DisplayName("기물 이동")
    class MovePiece {

        @Nested
        @DisplayName("차")
        class ChariotMove {

            @Test
            @DisplayName("차가 세로로 이동한다.")
            void 차가_세로로_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 3);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Chariot.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("차가 가로로 이동한다.")
            void 차가_가로로_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 2);
                board.movePiece(new Turn(Side.HAN), source, target);

                // when
                board.movePiece(new Turn(Side.HAN), target, Position.of(3, 2));

                // then
                assertThat(board.getBoard().get(Position.of(3, 2))).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("차가 대각선으로 이동하면 예외가 발생한다.")
            void 차가_대각선으로_이동하면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 1);
                Position target = Position.of(3, 3);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("상")
        class ElephantMove {

            @Test
            @DisplayName("상이 대각선 방향으로 이동한다.")
            void 상이_대각선_방향으로_이동한다() {
                // given - 상마상마 포진 사용
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Elephant.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("상이 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 상이_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 2);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("마")
        class HorseMove {

            @Test
            @DisplayName("마가 날 일자로 이동한다.")
            void 마가_날_일자로_이동한다() {
                // given - 마상마상 포진 사용 (2,1에 마가 있음)
                Board board = new Board(Formation.from("1"), Formation.from("4"));
                Position source = Position.of(2, 1);
                Position target = Position.of(3, 3);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Horse.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("마가 이동 불가능한 위치로 이동하면 예외가 발생한다.")
            void 마가_이동_불가능한_위치로_이동하면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("4"));
                Position source = Position.of(2, 1);
                Position target = Position.of(4, 4);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("포")
        class CannonMove {

            @Test
            @DisplayName("포가 기물을 하나 뛰어넘어 이동한다.")
            void 포가_기물을_하나_뛰어넘어_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                // 사(4,1)를 (4,3)으로 이동시켜 포가 뛰어넘을 기물 배치
                board.movePiece(new Turn(Side.HAN), Position.of(4, 1), Position.of(4, 2));
                board.movePiece(new Turn(Side.HAN), Position.of(4, 2), Position.of(4, 3));

                // when - (2,3) 포가 (4,3) 사를 뛰어넘어 (5,3)으로 이동
                board.movePiece(new Turn(Side.HAN), Position.of(2, 3), Position.of(5, 3));

                // then
                assertThat(board.getBoard().get(Position.of(5, 3))).isInstanceOf(Cannon.class);
            }

            @Test
            @DisplayName("포가 뛰어넘을 기물이 없으면 예외가 발생한다.")
            void 포가_뛰어넘을_기물이_없으면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(2, 3);
                Position target = Position.of(5, 3);

                // when & then - 중간에 기물이 없음
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("포가 포를 뛰어넘으면 예외가 발생한다.")
            void 포가_포를_뛰어넘으면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                // (8,3) 포를 (5,3)으로 이동하기 위해 먼저 중간에 기물 배치
                board.movePiece(new Turn(Side.HAN), Position.of(6, 1), Position.of(6, 2));
                board.movePiece(new Turn(Side.HAN), Position.of(6, 2), Position.of(6, 3));
                // (8,3) 포가 (6,3) 사를 뛰어넘어 (5,3)으로 이동
                board.movePiece(new Turn(Side.HAN), Position.of(8, 3), Position.of(5, 3));

                // 이제 (2,3) 포와 (5,3) 포 사이에 (5,3) 포를 뛰어넘는 상황 만들기
                // (2,3) 포가 (5,3) 포를 뛰어넘어 (7,3)으로 이동 시도
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), Position.of(2, 3), Position.of(7, 3)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("포를 넘어갈 수 없습니다.");
            }
        }

        @Nested
        @DisplayName("사")
        class GuardMove {

            @Test
            @DisplayName("사가 한 칸 이동한다.")
            void 사가_한_칸_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(4, 1);
                Position target = Position.of(5, 1);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Guard.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("사가 두 칸 이상 이동하면 예외가 발생한다.")
            void 사가_두_칸_이상_이동하면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(4, 1);
                Position target = Position.of(4, 3);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("궁")
        class KingMove {

            @Test
            @DisplayName("궁이 한 칸 이동한다.")
            void 궁이_한_칸_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 1);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(King.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("궁이 두 칸 이상 이동하면 예외가 발생한다.")
            void 궁이_두_칸_이상_이동하면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(5, 2);
                Position target = Position.of(5, 4);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("졸/병")
        class SoldierMove {

            @Test
            @DisplayName("졸이 앞으로 한 칸 이동한다.")
            void 졸이_앞으로_한_칸_이동한다() {
                // given - CHO 졸은 위로 이동
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 6);

                // when
                board.movePiece(new Turn(Side.CHO), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("병이 앞으로 한 칸 이동한다.")
            void 병이_앞으로_한_칸_이동한다() {
                // given - HAN 병은 아래로 이동
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 4);
                Position target = Position.of(1, 5);

                // when
                board.movePiece(new Turn(Side.HAN), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Soldier.class);
                assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
            }

            @Test
            @DisplayName("졸이 옆으로 한 칸 이동한다.")
            void 졸이_옆으로_한_칸_이동한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 7);
                Position target = Position.of(2, 7);

                // when
                board.movePiece(new Turn(Side.CHO), source, target);

                // then
                assertThat(board.getBoard().get(target)).isInstanceOf(Soldier.class);
            }

            @Test
            @DisplayName("졸이 뒤로 이동하면 예외가 발생한다.")
            void 졸이_뒤로_이동하면_예외가_발생한다() {
                // given - CHO 졸은 아래로 이동 불가
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 7);
                Position target = Position.of(1, 8);

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.CHO), source, target))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("적 기물 잡기")
        class CapturePiece {

            @Test
            @DisplayName("적 기물을 잡을 수 있다.")
            void 적_기물을_잡을_수_있다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));

                // 차를 이동시켜 적 졸을 잡기
                board.movePiece(new Turn(Side.HAN), Position.of(1, 4), Position.of(2, 4));
                board.movePiece(new Turn(Side.HAN), Position.of(1, 1), Position.of(1, 7));

                // then
                assertThat(board.getBoard().get(Position.of(1, 7))).isInstanceOf(Chariot.class);
                assertThat(board.getBoard().get(Position.of(1, 7)).isSameSide(Side.HAN)).isTrue();
            }

            @Test
            @DisplayName("아군 기물을 잡으면 예외가 발생한다.")
            void 아군_기물을_잡으면_예외가_발생한다() {
                // given
                Board board = new Board(Formation.from("1"), Formation.from("1"));
                Position source = Position.of(1, 1);
                Position target = Position.of(1, 4); // HAN 졸 위치

                // when & then
                Assertions.assertThatThrownBy(() -> board.movePiece(new Turn(Side.HAN), source, target))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("아군 기물은 잡을 수 없습니다.");
            }
        }
    }
}
