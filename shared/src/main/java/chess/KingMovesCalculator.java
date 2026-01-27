package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        List<ChessMove> returnedMoves = new ArrayList<>();

        // Get info on the king
        int curr_row = position.getRow();
        int curr_col = position.getColumn();
        ChessGame.TeamColor curr_color = board.getPiece(position).getTeamColor();

        // Somewhat simple. Just iterate through all 8 adjacent squares.
        // Easiest to iterate through all 9 squares including the one the king is in.
        // Skip that one the king is in.
        // Using modulo arithmetic should be pretty simple...
        for (int i = 0; i < 9; i++) {
            int radd = i % 3 - 1;
            int cadd = (i / 3) % 3 - 1;
            if (radd == 0 && cadd == 0)
                // a "move" that stays in place. Discard this.
                continue;

            ChessPosition addpos = new ChessPosition(curr_row + radd, curr_col + cadd);
            OpenTakeBlock isValid = moveValidityType(board, addpos, curr_color);
            if (isValid == OpenTakeBlock.OPEN || isValid == OpenTakeBlock.TAKE) {
                returnedMoves.add(new ChessMove(position,addpos, null));
            }
        }
        return returnedMoves;
    }
}
