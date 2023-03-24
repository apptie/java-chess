package chess.controller.state;

import chess.controller.GameCommand;
import chess.model.game.ChessGame;
import chess.model.position.Position;

public class Ready implements GameState {

    private final ChessGame chessGame;

    public Ready(final ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    @Override
    public GameState execute(final GameCommand gameCommand,
            final Position ignoredSource,
            final Position ignoredTarget
    ) {
        validateGameCommand(gameCommand);

        return handleGameCommand(gameCommand);
    }

    private GameState handleGameCommand(final GameCommand gameCommand) {
        if (gameCommand.isStart()) {
            chessGame.initialChessGame();
            return new Play(chessGame);
        }
        return new End();
    }

    private void validateGameCommand(final GameCommand gameCommand) {
        if (gameCommand.isMove()) {
            throw new IllegalArgumentException("게임이 시작되지 않았습니다.");
        }
    }

    @Override
    public boolean isContinue() {
        return true;
    }

    @Override
    public boolean isPlay() {
        return false;
    }

    @Override
    public boolean isPrintable() {
        return false;
    }
}