package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // There are 8 possible moves. Take two steps in one direction, one step in the other.
        // ... might want to use modulo arithmetic somehow?
        // Make sure one is +-2 while the other is +- 1.
        // I guess make each +-1, then pick one to multiply by 2.
        // Maybe make two loops, one for rows-is-2 and one for cols-is-2?

        // Initialize stuff for knight
        List<ChessMove> returnedMoves = new ArrayList<>();
        int curr_row = position.getRow();
        int curr_col = position.getColumn();
        ChessGame.TeamColor curr_color = board.getPiece(position).getTeamColor();

        // "Vertical" steps for knight
        for (int i = 0; i < 4; i++) {
            // Row add: +- 2
            int radd = (i % 2)*4 - 2;
            // Col add: +- 1
            int cadd = (i/2 % 2)*2 - 1;

            // Adding position
            ChessPosition addpos = new ChessPosition(curr_row + radd, curr_col + cadd);
            OpenTakeBlock moveValidity = moveValidityType(board, addpos, curr_color);
            if (moveValidity == OpenTakeBlock.TAKE || moveValidity == OpenTakeBlock.OPEN) {
                returnedMoves.add(new ChessMove(position,addpos,null));
            }
        }
        // "Horizontal" steps for knight
        for (int i = 0; i < 4; i++) {
            // Row add: +- 1
            int radd = (i % 2)*2 - 1;
            // Col add: +- 2
            int cadd = (i/2 % 2)*4 - 2;

            // Adding position
            ChessPosition addpos = new ChessPosition(curr_row + radd, curr_col + cadd);
            OpenTakeBlock moveValidity = moveValidityType(board, addpos, curr_color);
            if (moveValidity == OpenTakeBlock.TAKE || moveValidity == OpenTakeBlock.OPEN) {
                returnedMoves.add(new ChessMove(position,addpos,null));
            }
        }

        return returnedMoves;
    }
}
