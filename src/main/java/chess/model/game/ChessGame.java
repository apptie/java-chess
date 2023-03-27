package chess.model.game;

import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardFactory;
import chess.model.piece.Camp;
import chess.model.piece.score.PieceScore;
import chess.model.position.Position;
import chess.view.dto.ChessBoardResponse;

public class ChessGame {

    private ChessBoard chessBoard;
    private Turn turn;

    public void initialChessGame() {
        this.chessBoard = ChessBoardFactory.create();
        this.turn = new Turn();
    }

    public void move(final Position source, final Position target) {
        validateInitialChessGame();
        validatePosition(source, target);
        chessBoard.move(source, target, turn.findPlayer());
        turn.processNextTurn();
    }

    private void validateInitialChessGame() {
        if (chessBoard == null || turn == null) {
            throw new IllegalStateException("게임을 진행할 수 없는 상태입니다.");
        }
    }

    private void validatePosition(final Position source, final Position target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("동일한 위치로 기물을 이동시킬 수 없습니다.");
        }
    }

    public boolean isGameOnGoing() {
        return chessBoard.canPlayGame(turn.findPlayer());
    }

    public PieceScore getScoreByCamp(final Camp camp) {
        return chessBoard.calculateScoreByCamp(camp);
    }

    public Camp getCurrentCamp() {
        return turn.findPlayer();
    }

    public Camp getWinnerCamp() {
        return turn.oppositeCamp();
    }

    public ChessBoardResponse getChessBoard() {
        try {
            return new ChessBoardResponse(chessBoard.getBoard());
        } catch (NullPointerException e) {
            throw new IllegalStateException("게임을 시작하지 않았습니다.", e);
        }
    }
}
