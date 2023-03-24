package chess.model.game.state;

import static chess.helper.PositionFixture.A1;
import static chess.helper.PositionFixture.A2;
import static chess.helper.PositionFixture.A3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.controller.GameCommand;
import chess.controller.state.End;
import chess.controller.state.GameState;
import chess.controller.state.Play;
import chess.controller.state.Ready;
import chess.model.game.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReadyTest {

    private ChessGame chessGame;
    private GameState ready;

    @BeforeEach
    void beforeEach() {
        chessGame = new ChessGame();
        ready = new Ready(chessGame);
    }

    @Test
    @DisplayName("execute()는 명령어로 start가 주어지면 ChessGame의 필드를 초기화하고 Play를 반환한다.")
    void execute_givenStartCommand_thenInitialChessGameAndReturnPlay() {
        // when
        final GameState actual = ready.execute(GameCommand.START, A1, A1);

        // then
        assertThat(actual).isExactlyInstanceOf(Play.class);

        assertThat(chessGame.getChessBoard()).isNotNull();
    }

    @Test
    @DisplayName("execute()는 명령어로 move가 주어지면 예외가 발생한다.")
    void execute_givenMoveCommand_thenFail() {
        // when, then
        assertThatThrownBy(() -> ready.execute(GameCommand.MOVE, A2, A3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 시작되지 않았습니다.");
    }

    @Test
    @DisplayName("execute()는 명령어로 end가 주어지면 ChessGame을 초기화하지 않고 End를 반환한다.")
    void execute_givenEndCommand_thenNotInitialChessGameAndReturnEnd() {
        // when
        final GameState actual = ready.execute(GameCommand.END, A1, A1);

        // then
        assertThat(actual).isExactlyInstanceOf(End.class);

        assertThatThrownBy(() -> chessGame.getChessBoard())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임을 시작하지 않았습니다.");
    }

    @Test
    @DisplayName("isContinue()는 호출하면 true를 반환한다.")
    void isContinue_whenCall_thenReturnTrue() {
        // when
        final boolean actual = ready.isContinue();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("isPlay()는 호출하면 false를 반환한다.")
    void isPlay_whenCall_thenReturnFalse() {
        // when
        final boolean actual = ready.isPlay();

        // then
        assertThat(actual).isFalse();
    }
}
