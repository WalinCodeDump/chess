package chess;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class PawnMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Can move 2 at the start of the board (move on OPEN)
        // Can move 1 unless another piece is there (move on OPEN)
        // Can move diagonal 1 unless NO piece is there. (move on TAKE)

        // Initialize some things
        int curr_row = position.getRow();
        int curr_col = position.getColumn();
        ChessGame.TeamColor curr_color = board.getPiece(position).getTeamColor();
        List<ChessMove> returnedMoves = new ArrayList<>();

        // Constant for white or black; "color-determined direction"
        int cdd;
        if (curr_color == ChessGame.TeamColor.WHITE)
            cdd = 1;
        else
            cdd = -1;


        // 1 move forward
        // Prep for 2 moves forward with this "forward path clear" boolean:
        boolean fpclear = false;
        {
            // No promotion
            ChessPosition addpos = new ChessPosition(curr_row + cdd, curr_col);
            if (moveValidityType(board, addpos, curr_color) == OpenTakeBlock.OPEN) {
                if (curr_row + cdd != 1 && curr_row + cdd != 8) {
                    // No promotion
                    fpclear = true;
                    returnedMoves.add(new ChessMove(position, addpos, null));
                }
                else {
                    // Promotion
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.ROOK));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.KNIGHT));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.BISHOP));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.QUEEN));
                }
            }
        }

        // Diagonal take-moves
        for (int i = 0; i < 2; i++) {
            // i=0: go left/west. i=1: go right/east.
            int coladd = 2*i - 1;
            ChessPosition addpos = new ChessPosition(curr_row + cdd, curr_col + coladd);
            if (moveValidityType(board, addpos, curr_color) == OpenTakeBlock.TAKE) {
                if (curr_row + cdd != 1 && curr_row + cdd != 8)
                    // No promotion
                    returnedMoves.add(new ChessMove(position, addpos, null));
                else {
                    // Promotion
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.ROOK));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.KNIGHT));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.BISHOP));
                    returnedMoves.add(new ChessMove(position, addpos, ChessPiece.PieceType.QUEEN));
                }
            }
        }

        // 2 move forward
        boolean white_unmoved = (curr_row == 2 && curr_color == ChessGame.TeamColor.WHITE);
        boolean black_unmoved = (curr_row == 7 && curr_color == ChessGame.TeamColor.BLACK);
        if ((white_unmoved || black_unmoved) && fpclear) {
            ChessPosition addpos = new ChessPosition(curr_row + 2*cdd, curr_col);
            if (moveValidityType(board, addpos, curr_color) == OpenTakeBlock.OPEN)
                returnedMoves.add(new ChessMove(position, addpos, null));
        }
        return returnedMoves;
    }
}
