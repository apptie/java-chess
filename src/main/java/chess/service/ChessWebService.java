package chess.service;

import chess.dao.BoardDao;
import chess.dao.TurnDao;
import chess.domain.board.BasicChessBoardGenerator;
import chess.domain.board.Board;
import chess.domain.board.Position;
import chess.domain.board.Score;
import chess.domain.command.StatusResult;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.state.Ready;
import chess.domain.state.State;
import chess.dto.ApiResult;
import chess.dto.BoardSaveRequest;
import chess.dto.BoardUpdateRequest;
import chess.dto.FinishResponse;
import chess.dto.MoveCommand;
import chess.dto.MoveResponse;
import chess.dto.TurnFindResponse;
import chess.dto.TurnSaveRequest;
import chess.dto.TurnUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessWebService {

    private static final Color FIRST_TURN = Color.WHITE;

    private static final BoardDao BOARD_DAO = new BoardDao();
    private static final TurnDao TURN_DAO = new TurnDao();

    public ChessWebService() {
    }

    private State getState(int gameNumber) {
        TurnFindResponse response = TURN_DAO.findByGameNumber(gameNumber);
        if (response == null) {
            return Ready.start(new Board(new HashMap<>()));
        }
        return Ready.continueOf(response.getColor(), BOARD_DAO.findAllByGameNumber(gameNumber).getBoard());
    }

    public Map<String, Object> getModelWithBoard(int gameNumber) {
        State state = getState(gameNumber);
        return toModelMap(state.getBoard());
    }

    private Map<String, Object> toModelMap(Board board) {
        return board.getValue().entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getStringValue(), Map.Entry::getValue));
    }

    public ApiResult getStatus(int gameNumber) {
        State state = getState(gameNumber);
        Map<Color, Score> score = state.getScore();
        return ApiResult.succeed(new StatusResult(score));
    }

    public void initializeState(int gameNumber) {
        finishGame(gameNumber);

        State state = Ready.start(BasicChessBoardGenerator.generator());
        Map<Position, Piece> value = state.getBoard().getValue();
        value.forEach((position, piece) -> savePiece(position, piece, gameNumber));
        saveTurn(FIRST_TURN, gameNumber);
    }

    public ApiResult finishGame(int gameNumber) {
        try {
            deleteAllPiece(gameNumber);
            deleteTurn(gameNumber);
            return ApiResult.succeed(new FinishResponse(gameNumber));
        } catch (RuntimeException e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    public ApiResult movePiece(MoveCommand command) {
        int gameNumber = command.getGameNumber();
        State state = getState(gameNumber);
        Position source = Position.valueOf(command.getSource());
        Position destination = Position.valueOf(command.getDestination());
        try {
            state = state.movePiece(source, destination);
            updatePiece(source, destination, gameNumber);
            updateTurn(state.getTurn(), gameNumber);
            return ApiResult
                    .succeed(new MoveResponse(command.getSource(), command.getDestination(), isFinished(state)));
        } catch (RuntimeException e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    private boolean isFinished(State state) {
        return state.isFinished();
    }

    private void savePiece(Position position, Piece piece, int gameNumber) {
        BOARD_DAO.save(new BoardSaveRequest(position, piece, gameNumber));
    }

    private void saveTurn(Color color, int gameNumber) {
        TURN_DAO.save(new TurnSaveRequest(color, gameNumber));
    }

    private void updatePiece(Position source, Position destination, int gameNumber) {
        BOARD_DAO.updateByPosition(new BoardUpdateRequest(destination, source, gameNumber));
        BOARD_DAO.delete(source, gameNumber);
    }

    private void updateTurn(Color color, int gameNumber) {
        TURN_DAO.update(new TurnUpdateRequest(color, gameNumber));
    }

    private void deleteAllPiece(int gameNumber) {
        BOARD_DAO.deleteAllByGameNumber(gameNumber);
    }

    private void deleteTurn(int gameNumber) {
        TURN_DAO.deleteByGameNumber(gameNumber);
    }
}