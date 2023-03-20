package chess.model.board;

import static chess.helper.PositionFixture.A1;
import static chess.helper.PositionFixture.A2;
import static chess.helper.PositionFixture.A7;
import static chess.helper.PositionFixture.B1;
import static chess.helper.PositionFixture.B2;
import static chess.helper.PositionFixture.B7;
import static chess.helper.PositionFixture.B8;
import static chess.helper.PositionFixture.C1;
import static chess.helper.PositionFixture.C2;
import static chess.helper.PositionFixture.C7;
import static chess.helper.PositionFixture.C8;
import static chess.helper.PositionFixture.D1;
import static chess.helper.PositionFixture.D2;
import static chess.helper.PositionFixture.D7;
import static chess.helper.PositionFixture.D8;
import static chess.helper.PositionFixture.E1;
import static chess.helper.PositionFixture.E2;
import static chess.helper.PositionFixture.E7;
import static chess.helper.PositionFixture.E8;
import static chess.helper.PositionFixture.F1;
import static chess.helper.PositionFixture.F2;
import static chess.helper.PositionFixture.F7;
import static chess.helper.PositionFixture.F8;
import static chess.helper.PositionFixture.G1;
import static chess.helper.PositionFixture.G8;
import static chess.helper.PositionFixture.H1;
import static chess.helper.PositionFixture.H7;
import static chess.helper.PositionFixture.H8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import chess.helper.PositionFixture;
import chess.model.piece.Camp;
import chess.model.piece.Piece;
import chess.model.piece.type.Bishop;
import chess.model.piece.type.InitialPawn;
import chess.model.piece.type.King;
import chess.model.piece.type.Knight;
import chess.model.piece.type.Queen;
import chess.model.piece.type.Rook;
import chess.model.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardFactoryTest {

    @Test
    @DisplayName("create()는 호출하면 ChessBoard를 생성해 반환한다")
    void create_whenCall_thenReturnChessBoard() {
        final ChessBoard chessBoard = assertDoesNotThrow(ChessBoardFactory::create);

        assertThat(chessBoard).isExactlyInstanceOf(ChessBoard.class);
    }

    @Test
    @DisplayName("생성한 ChessBoard는 게임 시작을 위한 32개의 기물이 정해진 위치에 존재한다")
    void create_whenCall_thenReturnPrepareGameStart() {
        final ChessBoard chessBoard = ChessBoardFactory.create();
        final Map<Position, Piece> board = chessBoard.getBoard();

        // 크기 확인(64개)
        assertThat(board.keySet().size()).isSameAs(64);

        // 흰색 진영
        final Piece a1 = board.get(A1);
        assertThat(a1).isExactlyInstanceOf(Rook.class);
        assertThat(a1.camp()).isSameAs(Camp.WHITE);
        final Piece b1 = board.get(B1);
        assertThat(b1).isExactlyInstanceOf(Knight.class);
        assertThat(b1.camp()).isSameAs(Camp.WHITE);
        final Piece c1 = board.get(C1);
        assertThat(c1).isExactlyInstanceOf(Bishop.class);
        assertThat(c1.camp()).isSameAs(Camp.WHITE);
        final Piece d1 = board.get(D1);
        assertThat(d1).isExactlyInstanceOf(Queen.class);
        assertThat(d1.camp()).isSameAs(Camp.WHITE);
        final Piece e1 = board.get(E1);
        assertThat(e1).isExactlyInstanceOf(King.class);
        assertThat(e1.camp()).isSameAs(Camp.WHITE);
        final Piece f1 = board.get(F1);
        assertThat(f1).isExactlyInstanceOf(Bishop.class);
        assertThat(f1.camp()).isSameAs(Camp.WHITE);
        final Piece g1 = board.get(G1);
        assertThat(g1).isExactlyInstanceOf(Knight.class);
        assertThat(g1.camp()).isSameAs(Camp.WHITE);
        final Piece h1 = board.get(H1);
        assertThat(h1).isExactlyInstanceOf(Rook.class);
        assertThat(h1.camp()).isSameAs(Camp.WHITE);
        final Piece a2 = board.get(A2);
        assertThat(a2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(a2.camp()).isSameAs(Camp.WHITE);
        final Piece b2 = board.get(B2);
        assertThat(b2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(b2.camp()).isSameAs(Camp.WHITE);
        final Piece c2 = board.get(C2);
        assertThat(c2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(c2.camp()).isSameAs(Camp.WHITE);
        final Piece d2 = board.get(D2);
        assertThat(d2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(d2.camp()).isSameAs(Camp.WHITE);
        final Piece e2 = board.get(E2);
        assertThat(e2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(e2.camp()).isSameAs(Camp.WHITE);
        final Piece f2 = board.get(F2);
        assertThat(f2).isExactlyInstanceOf(InitialPawn.class);
        assertThat(f2.camp()).isSameAs(Camp.WHITE);

        // 검은색 진영
        final Piece a8 = board.get(PositionFixture.A8);
        assertThat(a8).isExactlyInstanceOf(Rook.class);
        assertThat(a8.camp()).isSameAs(Camp.BLACK);
        final Piece b8 = board.get(B8);
        assertThat(b8).isExactlyInstanceOf(Knight.class);
        assertThat(b8.camp()).isSameAs(Camp.BLACK);
        final Piece c8 = board.get(C8);
        assertThat(c8).isExactlyInstanceOf(Bishop.class);
        assertThat(c8.camp()).isSameAs(Camp.BLACK);
        final Piece d8 = board.get(D8);
        assertThat(d8).isExactlyInstanceOf(Queen.class);
        assertThat(d8.camp()).isSameAs(Camp.BLACK);
        final Piece e8 = board.get(E8);
        assertThat(e8).isExactlyInstanceOf(King.class);
        assertThat(e8.camp()).isSameAs(Camp.BLACK);
        final Piece f8 = board.get(F8);
        assertThat(f8).isExactlyInstanceOf(Bishop.class);
        assertThat(f8.camp()).isSameAs(Camp.BLACK);
        final Piece g8 = board.get(G8);
        assertThat(g8).isExactlyInstanceOf(Knight.class);
        assertThat(g8.camp()).isSameAs(Camp.BLACK);
        final Piece h8 = board.get(H8);
        assertThat(h8).isExactlyInstanceOf(Rook.class);
        assertThat(h8.camp()).isSameAs(Camp.BLACK);
        final Piece a7 = board.get(A7);
        assertThat(a7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(a7.camp()).isSameAs(Camp.BLACK);
        final Piece b7 = board.get(B7);
        assertThat(b7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(b7.camp()).isSameAs(Camp.BLACK);
        final Piece c7 = board.get(C7);
        assertThat(c7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(c7.camp()).isSameAs(Camp.BLACK);
        final Piece d7 = board.get(D7);
        assertThat(d7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(d7.camp()).isSameAs(Camp.BLACK);
        final Piece e7 = board.get(E7);
        assertThat(e7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(e7.camp()).isSameAs(Camp.BLACK);
        final Piece f7 = board.get(F7);
        assertThat(f7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(f7.camp()).isSameAs(Camp.BLACK);
        final Piece h7 = board.get(H7);
        assertThat(h7).isExactlyInstanceOf(InitialPawn.class);
        assertThat(h7.camp()).isSameAs(Camp.BLACK);
    }
}
