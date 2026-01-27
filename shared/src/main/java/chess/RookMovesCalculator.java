package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Start writing...
        // 1 - Move around in all four directions.
        // 2 - Do checks
        // 2a - Is off board? Strike it.
        // 2b - Collides with own color? Strike it.
        // 2c - Is past another piece? Strike it.

        // So... - Iterate moving outwards first. Stop iterating after any of the above.
        // Actually, also stop iterating a direction after encountering opposite color.
        List<ChessMove> returnedMoves = new ArrayList<>();

        int curr_row = position.getRow();
        int curr_col = position.getColumn();
        ChessGame.TeamColor curr_color = board.getPiece(position).getTeamColor();

        // North
        for (int i = 1; i <= 8; i++) {
            // Create new position (will remove later if it collides)
            ChessPosition addpos = new ChessPosition(curr_row + i, curr_col);

            // Check for collisions (with edge or other pieces), and add move if it is valid
            OpenTakeBlock moveVT = moveValidityType(board, addpos, curr_color);
            if (moveVT == OpenTakeBlock.OPEN) {
                // Nothing is in the space being considered.
                // The move is valid and the loop continues.
                returnedMoves.add(new ChessMove(position, addpos, null));
            }
            else if (moveVT == OpenTakeBlock.TAKE) {
                // A piece of the opposite color is there.
                // Can take, so the move is valid, but line of sight is blocked.
                returnedMoves.add(new ChessMove(position, addpos, null));
                break;
            }
            else {
                // moveVT has the BLOCK value; cannot move there.
                // Either the same color is there, or it is off the board.
                break;
            }
        }
        // West
        for (int i = 1; i <= 8; i++) {
            // Almost the same as North up top.
            // Only difference is column decrements, not row incrementing.
            ChessPosition addpos = new ChessPosition(curr_row, curr_col - i);

            // Check for collisions, etc.
            OpenTakeBlock moveVT = moveValidityType(board, addpos, curr_color);
            if (moveVT == OpenTakeBlock.OPEN) {
                returnedMoves.add(new ChessMove(position, addpos, null));
            }
            else if (moveVT == OpenTakeBlock.TAKE) {
                returnedMoves.add(new ChessMove(position, addpos, null));
                break;
            }
            else {
                break;
            }
        }
        // South
        for (int i = 1; i <= 8; i++){
            // Almost the same as North up top.
            // Only difference is row decrements.
            ChessPosition addpos = new ChessPosition(curr_row - i, curr_col);

            // Check for collisions, etc.
            OpenTakeBlock moveVT = moveValidityType(board, addpos, curr_color);
            if (moveVT == OpenTakeBlock.OPEN) {
                returnedMoves.add(new ChessMove(position, addpos, null));
            }
            else if (moveVT == OpenTakeBlock.TAKE) {
                returnedMoves.add(new ChessMove(position, addpos, null));
                break;
            }
            else {
                break;
            }
        }
        // East
        for (int i = 1; i <= 8; i++){
            // Almost the same as Northeast up top.
            // Only difference is column increments.
            ChessPosition addpos = new ChessPosition(curr_row, curr_col + i);

            // Check for collisions, etc.
            OpenTakeBlock moveVT = moveValidityType(board, addpos, curr_color);
            if (moveVT == OpenTakeBlock.OPEN) {
                returnedMoves.add(new ChessMove(position, addpos, null));
            }
            else if (moveVT == OpenTakeBlock.TAKE) {
                returnedMoves.add(new ChessMove(position, addpos, null));
                break;
            }
            else {
                break;
            }
        }
        return returnedMoves;
    }

}
