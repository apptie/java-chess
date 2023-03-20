package chess.model.piece.type;

import chess.model.piece.Camp;
import chess.model.piece.Direction;
import chess.model.piece.Piece;
import chess.model.position.Distance;
import java.util.List;

public class Rook extends Piece {

    private static final List<Direction> availableDirections = Direction.ORTHOGONAL;

    public Rook(final Camp camp) {
        super(camp);
    }

    @Override
    protected boolean movable(final Distance distance) {
        return availableDirections.stream()
                .anyMatch(distance::matchByDirection);
    }
}