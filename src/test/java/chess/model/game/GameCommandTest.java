package chess.model.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GameCommandTest {

    @ParameterizedTest(name = "입력한 커맨드가 {0}이라면 GameCommand.{1}을 반환한다")
    @DisplayName("findGameCommand() 성공 테스트")
    @CsvSource(value = {"start:START", "end:END", "move:MOVE"}, delimiter = ':')
    void findGameCommand_givenValidCommand_thenReturnCommand(final String command, final GameCommand expected) {
        // when
        final GameCommand gameCommand = GameCommand.findGameCommand(command);

        // then
        assertThat(gameCommand).isSameAs(expected);
    }

    @Test
    @DisplayName("입력한 커맨드가 유효하지 않은 커맨드라면 예외가 발생한다.")
    void findGameCommand_givenInvalidCommand_thenFail() {
        // when, then
        assertThatThrownBy(() -> GameCommand.findGameCommand("z"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 명령어입니다.");
    }
}