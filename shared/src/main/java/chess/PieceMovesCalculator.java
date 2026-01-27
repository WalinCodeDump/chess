package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);

    enum OpenTakeBlock {
        OPEN, // Position does not have a piece occupying it
        TAKE, // Position has a piece of the opposite color to take
        BLOCK // Position is off board or same color; cannot take
    }


    default OpenTakeBlock moveValidityType(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        // Within rows of chess board?
        if (position.getRow() < 1 || position.getRow() > 8) {
            return OpenTakeBlock.BLOCK;
        }

        // Within columns of chess board?
        if (position.getColumn() < 1 || position.getColumn() > 8) {
            return OpenTakeBlock.BLOCK;
        }

        // Another piece at the spot being moved to?
        if (board.getPiece(position) != null) {
            // Check if it's the same color or not
            if (board.getPiece(position).getTeamColor() == color) {
                // System.out.println("blocked via color; OpenTakeBlock mvt PieceMovesCalculator");
                // Other position is same color; it is blocked
                return OpenTakeBlock.BLOCK;
            } else {
                // Other position is opposite color; can take
                return OpenTakeBlock.TAKE;
            }

        }

        // Last option. No other pieces have blocked the move, and it is on the board.
        return OpenTakeBlock.OPEN;
    }
}
