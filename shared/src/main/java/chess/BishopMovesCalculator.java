package chess;

import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Start writing...
        // 1 - Move around in all four directions.
        // 2 - Do checks
        // 2a - Is off board? Strike it.
        // 2b - Collides with own color? Strike it.
        // 2c - Is past another piece? Strike it.

        // So... - Iterate moving outwards first. Stop iterating after any of the above.
        // Actually, also stop iterating a direction after encountering opposite color.

        int curr_row = position.getRow();
        int curr_col = position.getColumn();

        // Northwest
        for (int i = 1; i <= 8; i++) {
            // Check for collisions! With edge, with piece
            // Collision with edge:
            if (curr_row + i > 8 || curr_col + i > 8)
                break;

            // Create new position (will remove later if it collides)
            ChessPosition addpos = new ChessPosition(curr_row + i, curr_col + i);

            // Recreate the piece for clarity
            // Does not check if it's the right type...assuming that it's already
            // been checked.
            ChessGame.TeamColor curr_color = board.getPiece(position).getTeamColor();
            ChessPiece curr_piece = new ChessPiece(curr_color, ChessPiece.PieceType.BISHOP);
            positionIsValid(board,position,curr_piece);
        }
        // Northeast
        // Southeast
        // Southwest
    }
}
