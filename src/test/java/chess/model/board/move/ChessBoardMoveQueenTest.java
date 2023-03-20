package chess.model.board.move;

import static chess.helper.PositionFixture.A1;
import static chess.helper.PositionFixture.A2;
import static chess.helper.PositionFixture.A7;
import static chess.helper.PositionFixture.B2;
import static chess.helper.PositionFixture.B7;
import static chess.helper.PositionFixture.C2;
import static chess.helper.PositionFixture.C4;
import static chess.helper.PositionFixture.C5;
import static chess.helper.PositionFixture.C6;
import static chess.helper.PositionFixture.C7;
import static chess.helper.PositionFixture.D2;
import static chess.helper.PositionFixture.D4;
import static chess.helper.PositionFixture.D5;
import static chess.helper.PositionFixture.D6;
import static chess.helper.PositionFixture.D7;
import static chess.helper.PositionFixture.D8;
import static chess.helper.PositionFixture.E2;
import static chess.helper.PositionFixture.E4;
import static chess.helper.PositionFixture.E5;
import static chess.helper.PositionFixture.E6;
import static chess.helper.PositionFixture.E7;
import static chess.helper.PositionFixture.F2;
import static chess.helper.PositionFixture.F7;
import static chess.helper.PositionFixture.G2;
import static chess.helper.PositionFixture.G7;
import static chess.helper.PositionFixture.H2;
import static chess.helper.PositionFixture.H7;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.helper.ChessBoardPieceMovingHelper;
import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardFactory;
import chess.model.piece.Camp;
import chess.model.position.File;
import chess.model.position.Position;
import chess.model.position.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChessBoardMoveQueenTest {

    @Nested
    @DisplayName("move() 검은색 퀸 테스트")
    class BlackQueenTest {

        private final Position source = D5;
        private ChessBoard chessBoard;

        @BeforeEach
        void beforeEach() {
            chessBoard = ChessBoardFactory.create();
            ChessBoardPieceMovingHelper.move(chessBoard, D8, source);
        }

        @ParameterizedTest(name = "검은색 퀸은 경로에 아무것도 없고 목적지가 빈 칸일 때 ({0}, {1}) 방향으로 이동할 수 있다.")
        @DisplayName("move() 유효한 이동 방향, 유효한 이동 거리, 유효한 경로, 빈 칸 테스트")
        @CsvSource(value = {
                "D:SIXTH", "C:FIFTH", "D:FOURTH", "E:FIFTH", "C:SIXTH", "E:SIXTH", "C:FOURTH", "E:FOURTH"
        }, delimiter = ':')
        void move_givenValidAllAndEmptyTarget_thenSuccess(final File targetFile, final Rank targetRank) {
            // given
            final Position targetPosition = Position.of(targetFile, targetRank);

            // when, then
            assertThatCode(() -> chessBoard.move(source, targetPosition, Camp.BLACK)).doesNotThrowAnyException();
        }

        @ParameterizedTest(name = "검은색 퀸은 경로에 아무것도 없고 목적지가 적군일 때 ({0}, {1}) 방향으로 이동할 수 있다.")
        @DisplayName("move() 유효한 이동 방향, 유효한 이동 거리, 유효한 경로, 빈 칸 테스트")
        @CsvSource(value = {
                "D:SIXTH", "C:FIFTH", "D:FOURTH", "E:FIFTH", "C:SIXTH", "E:SIXTH", "C:FOURTH", "E:FOURTH"
        }, delimiter = ':')
        void move_givenValidAllAndEnemyTarget_thenSuccess(final File targetFile, final Rank targetRank) {
            // given
            final Position targetPosition = Position.of(targetFile, targetRank);
            ChessBoardPieceMovingHelper.move(chessBoard, A1, targetPosition);

            // when, then
            assertThatCode(() -> chessBoard.move(source, targetPosition, Camp.BLACK)).doesNotThrowAnyException();
        }

        @ParameterizedTest(name = "검은색 퀸은 경로에 아무것도 없고 목적지가 아군일 때 ({0}, {1}) 방향으로 이동할 수 없다.")
        @DisplayName("move() 유효한 이동 방향, 유효한 이동 거리, 유효한 경로, 아군 칸 테스트")
        @CsvSource(value = {
                "D:SIXTH", "C:FIFTH", "D:FOURTH", "E:FIFTH", "C:SIXTH", "E:SIXTH", "C:FOURTH", "E:FOURTH"
        }, delimiter = ':')
        void move_givenValidAllAndAllyTarget_thenSuccess(final File targetFile, final Rank targetRank) {
            // given
            final Position targetPosition = Position.of(targetFile, targetRank);
            ChessBoardPieceMovingHelper.move(chessBoard, A7, targetPosition);

            // when, then
            assertThatThrownBy(() -> chessBoard.move(source, targetPosition, Camp.BLACK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 기물은 지정한 목적지로 움직일 수 없습니다.");
        }

        @ParameterizedTest(name = "검은색 퀸은 경로에 아군 기물이 있다면 목적지와 무관하게 ({0}, {1}) 방향으로 이동할 수 없다.")
        @DisplayName("move() 유효한 이동 방향, 유효한 이동 거리, 유효한 목적지, 경로 중 아군 기물을 만날 경우 테스트")
        @CsvSource(value = {
                "D:SEVENTH", "B:FIFTH", "D:THIRD", "F:FIFTH", "B:SEVENTH", "F:SEVENTH", "F:THIRD", "B:THIRD"
        }, delimiter = ':')
        void move_givenInvalidEnemyWayPoint_thenFail(final File targetFile, final Rank targetRank) {
            // given
            ChessBoardPieceMovingHelper.move(chessBoard, A7, D6);
            ChessBoardPieceMovingHelper.move(chessBoard, B7, C5);
            ChessBoardPieceMovingHelper.move(chessBoard, C7, D4);
            ChessBoardPieceMovingHelper.move(chessBoard, D7, E5);
            ChessBoardPieceMovingHelper.move(chessBoard, E7, C6);
            ChessBoardPieceMovingHelper.move(chessBoard, F7, E6);
            ChessBoardPieceMovingHelper.move(chessBoard, G7, C4);
            ChessBoardPieceMovingHelper.move(chessBoard, H7, E4);

            final Position targetPosition = Position.of(targetFile, targetRank);

            // when, then
            assertThatThrownBy(() -> chessBoard.move(source, targetPosition, Camp.BLACK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 경로로 이동할 수 없습니다.");
        }

        @ParameterizedTest(name = "검은색 퀸은 경로에 적군 기물이 있다면 목적지와 무관하게 ({0}, {1}) 방향으로 이동할 수 없다.")
        @DisplayName("move() 유효한 이동 방향, 유효한 이동 거리, 유효한 목적지, 경로 중 적군 기물을 만날 경우 테스트")
        @CsvSource(value = {
                "D:SEVENTH", "B:FIFTH", "D:THIRD", "F:FIFTH", "B:SEVENTH", "F:SEVENTH", "F:THIRD", "B:THIRD"
        }, delimiter = ':')
        void move_givenInvalidAllyWayPoint_thenFail(final File targetFile, final Rank targetRank) {
            // given
            ChessBoardPieceMovingHelper.move(chessBoard, A2, D6);
            ChessBoardPieceMovingHelper.move(chessBoard, B2, C5);
            ChessBoardPieceMovingHelper.move(chessBoard, C2, D4);
            ChessBoardPieceMovingHelper.move(chessBoard, D2, E5);
            ChessBoardPieceMovingHelper.move(chessBoard, E2, C6);
            ChessBoardPieceMovingHelper.move(chessBoard, F2, E6);
            ChessBoardPieceMovingHelper.move(chessBoard, G2, C4);
            ChessBoardPieceMovingHelper.move(chessBoard, H2, E4);

            final Position targetPosition = Position.of(targetFile, targetRank);

            // when, then
            assertThatThrownBy(() -> chessBoard.move(source, targetPosition, Camp.BLACK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 경로로 이동할 수 없습니다.");
        }
    }
}
