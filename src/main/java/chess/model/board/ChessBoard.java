package chess.model.board;

import chess.model.piece.Camp;
import chess.model.piece.Direction;
import chess.model.piece.Piece;
import chess.model.piece.PieceScore;
import chess.model.piece.PieceScoreConverter;
import chess.model.piece.type.Empty;
import chess.model.position.Distance;
import chess.model.position.File;
import chess.model.position.Position;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessBoard {

    private static final int IS_PLACED = 1;
    private static final int IS_NOT_PLACED = 0;

    private final Map<Position, Piece> board;

    ChessBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(final Position source, final Position target, final Camp camp) {
        final Piece sourcePiece = board.get(source);

        validateSource(sourcePiece, camp);
        validateTotalWayPoint(source, target);

        final Piece targetPiece = board.get(target);
        final Distance distance = target.differ(source);

        validateMovable(sourcePiece, targetPiece, distance);
        updateBoard(source, sourcePiece, target);
    }

    private void validateSource(final Piece sourcePiece, final Camp camp) {
        if (!sourcePiece.isSameTeam(camp)) {
            throw new IllegalArgumentException("게임을 진행하는 플레이어의 기물이 아닙니다.");
        }
    }

    private void validateTotalWayPoint(final Position source, final Position target) {
        final Distance distance = target.differ(source);
        final Direction direction = distance.findDirection();

        findWayPoint(source, target, direction);
    }

    private void findWayPoint(final Position source, final Position target, final Direction direction) {
        Position wayPoint = source.findNextPosition(direction);
        source.findNextPosition(direction);

        while (!wayPoint.equals(target)) {
            validateTargetWayPoint(wayPoint);
            wayPoint = wayPoint.findNextPosition(direction);
        }
    }

    private void validateTargetWayPoint(final Position wayPoint) {
        final Piece wayPointPiece = board.get(wayPoint);

        if (wayPointPiece.isNotPassable()) {
            throw new IllegalArgumentException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private void validateMovable(final Piece sourcePiece, final Piece targetPiece, final Distance distance) {
        if (!sourcePiece.movable(distance, targetPiece)) {
            throw new IllegalArgumentException("해당 기물은 지정한 목적지로 움직일 수 없습니다.");
        }
    }

    private void updateBoard(final Position source, final Piece sourcePiece, final Position target) {
        board.put(target, sourcePiece.pick());
        board.put(source, Empty.EMPTY_PIECE);
    }

    public boolean canPlayGame(final Camp camp) {
        return board.keySet().stream()
                .anyMatch(position -> isAliveKing(position, camp));
    }

    private boolean isAliveKing(final Position position, final Camp camp) {
        final Piece piece = board.get(position);

        return piece.isSameTeam(camp) && piece.isKing();
    }

    public PieceScore calculateScoreByCamp(final Camp camp) {
        final List<PieceScore> campPieceScore = convertToPieceScoreByCamp(camp);
        final PieceScore totalPieceScore = calculateTotalPieceScore(campPieceScore);
        final PieceScore pawnOffsetScore = calculateTotalPawnOffsetScore(camp);

        return totalPieceScore.minus(pawnOffsetScore);
    }

    private List<PieceScore> convertToPieceScoreByCamp(final Camp camp) {
        return board.keySet().stream()
                .map(board::get)
                .filter(piece -> piece.isSameTeam(camp))
                .map(piece -> PieceScoreConverter.convert(piece.getClass()))
                .collect(Collectors.toList());
    }

    private PieceScore calculateTotalPieceScore(final List<PieceScore> campPieceScore) {
        return campPieceScore.stream()
                .reduce(PieceScore.ZERO, PieceScore::plus);
    }

    private PieceScore calculateTotalPawnOffsetScore(final Camp camp) {
        return Arrays.stream(File.values())
                .map(file -> calculatePawnOffsetScoreByFile(file, camp))
                .reduce(PieceScore.ZERO, PieceScore::plus);
    }

    private PieceScore calculatePawnOffsetScoreByFile(final File file, final Camp camp) {
        final Position endPosition = Position.of(file, Rank.EIGHTH);
        Position targetPosition = Position.of(file, Rank.FIRST);
        int count = 0;

        while (!targetPosition.equals(endPosition)) {
            count += findSameFilePawn(camp, targetPosition);
            targetPosition = targetPosition.findNextPosition(Direction.NORTH);
        }

        return calculatePawnOffsetScoreByFilePawnCount(count);
    }

    private int findSameFilePawn(final Camp camp, final Position targetPosition) {
        if (board.get(targetPosition).isPawn() && board.get(targetPosition).isSameTeam(camp)) {
            return IS_PLACED;
        }
        return IS_NOT_PLACED;
    }

    private PieceScore calculatePawnOffsetScoreByFilePawnCount(final int count) {
        if (count < 2) {
            return PieceScore.ZERO;
        }

        PieceScore pawnOffsetScore = PieceScore.ZERO;

        for (int i = 0; i < count; i++) {
            pawnOffsetScore = pawnOffsetScore.plus(PieceScore.PAWN_OFFSET);
        }
        return pawnOffsetScore;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
