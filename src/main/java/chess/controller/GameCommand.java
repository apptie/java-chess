package chess.controller;

import java.util.Arrays;

public enum GameCommand {

    START("start"),
    END("end");

    private final String command;

    GameCommand(final String command) {
        this.command = command;
    }

    public static GameCommand from(final String input) {
        return Arrays.stream(values()).filter(gameCommand -> gameCommand.match(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커맨드입니다."));
    }

    private boolean match(final String input) {
        return this.command.equals(input);
    }
}