package chess.model.piece;

import chess.model.Color;
import chess.model.position.Distance;
import java.util.List;

public class Bishop extends Piece {

    private static final List<Direction> directions = Direction.bishop();

    public Bishop(final Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    boolean movable(final Distance distance) {
        return false;
    }
}
