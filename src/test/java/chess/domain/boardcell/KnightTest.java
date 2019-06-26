package chess.domain.boardcell;

import chess.domain.ChessGame;
import chess.domain.CoordinatePair;
import chess.domain.LivingPieceGroup;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {

    @Test
    void getMovable() {
        ChessPiece rb = ChessPieceFactory.create(PieceType.ROOK_BLACK);
        ChessPiece bb = ChessPieceFactory.create(PieceType.BISHOP_BLACK);
        ChessPiece rw = ChessPieceFactory.create(PieceType.ROOK_WHITE);
        ChessPiece bw = ChessPieceFactory.create(PieceType.BISHOP_WHITE);
        ChessPiece nw = ChessPieceFactory.create(PieceType.KNIGHT_WHITE);
        Map<CoordinatePair, ChessPiece> pieces = new HashMap<>();
        pieces.put(CoordinatePair.of("a8").get(), rb);
        pieces.put(CoordinatePair.of("c8").get(), bb);
        pieces.put(CoordinatePair.of("f8").get(), bb);
        pieces.put(CoordinatePair.of("h8").get(), rb);
        pieces.put(CoordinatePair.of("b3").get(), nw);
        pieces.put(CoordinatePair.of("a1").get(), rw);
        pieces.put(CoordinatePair.of("c1").get(), bw);
        pieces.put(CoordinatePair.of("f1").get(), bw);
        pieces.put(CoordinatePair.of("h1").get(), rw);

        ChessGame board = new ChessGame(() -> LivingPieceGroup.of(pieces));

        assertThat(nw.getMovableCoordinates(board::getTeamAt, CoordinatePair.of("b3").get())).containsExactlyInAnyOrder(
            CoordinatePair.of("d2").get(),
            CoordinatePair.of("d4").get(),
            CoordinatePair.of("c5").get(),
            CoordinatePair.of("a5").get()
        );

    }

    @Test
    void getMovable_enemy() {
        ChessPiece rb = ChessPieceFactory.create(PieceType.ROOK_BLACK);
        ChessPiece bb = ChessPieceFactory.create(PieceType.BISHOP_BLACK);
        ChessPiece rw = ChessPieceFactory.create(PieceType.ROOK_WHITE);
        ChessPiece bw = ChessPieceFactory.create(PieceType.BISHOP_WHITE);
        ChessPiece nw = ChessPieceFactory.create(PieceType.KNIGHT_WHITE);
        Map<CoordinatePair, ChessPiece> pieces = new HashMap<>();
        pieces.put(CoordinatePair.of("a8").get(), rb);
        pieces.put(CoordinatePair.of("c8").get(), bb);
        pieces.put(CoordinatePair.of("f8").get(), bb);
        pieces.put(CoordinatePair.of("h8").get(), rb);
        pieces.put(CoordinatePair.of("b6").get(), nw);
        pieces.put(CoordinatePair.of("a1").get(), rw);
        pieces.put(CoordinatePair.of("c1").get(), bw);
        pieces.put(CoordinatePair.of("f1").get(), bw);
        pieces.put(CoordinatePair.of("h1").get(), rw);

        ChessGame board = new ChessGame(() -> LivingPieceGroup.of(pieces));

        assertThat(nw.getMovableCoordinates(board::getTeamAt, CoordinatePair.of("b6").get())).containsExactlyInAnyOrder(
            CoordinatePair.of("a8").get(),
            CoordinatePair.of("c8").get(),
            CoordinatePair.of("d7").get(),
            CoordinatePair.of("d5").get(),
            CoordinatePair.of("a4").get(),
            CoordinatePair.of("c4").get()
        );

    }
}