package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        var row = myPosition.getRow();
        var col = myPosition.getColumn();
        var start = new ChessPosition(row,col);

        var returnMoves = new HashSet<ChessMove>();
        switch(type) {
            case KING -> {
                // Any valid position within 1 square of current, including diagonals.
                // Initialized below, then later added based on current square
                var notPromoted = PieceType.KING;

                // ------ Up and down ------
                // U stands for "Up" because the piece moves one square up
                var endU = new ChessPosition(row+1,col);
                var moveU = new ChessMove(start, endU, notPromoted);
                // D stands for "Down" because the piece moves one square down
                var endD = new ChessPosition(row-1, col);
                var moveD = new ChessMove(start, endD, notPromoted);

                // -------Right side--------
                // UR stands for "Up Right" because the piece moves one square up and one right
                var endUR = new ChessPosition(row+1, col+1);
                var moveUR = new ChessMove(start, endUR, notPromoted);
                // R stands for "Right" because the piece moves one square right
                var endR = new ChessPosition(row, col+1);
                var moveR = new ChessMove(start, endR, notPromoted);
                // DR stands for "Down Right" because the piece moves one square down and one right
                var endDR = new ChessPosition(row-1,col+1);
                var moveDR = new ChessMove(start, endDR, notPromoted);

                // -------Left side--------
                // DL stands for "Down Left" because the piece moves one square down and one left
                var endDL = new ChessPosition(row-1, col-1);
                var moveDL = new ChessMove(start, endDL, notPromoted);
                // L stands for "Left" because the piece moves one square to the left
                var endL = new ChessPosition(row,col-1);
                var moveL = new ChessMove(start, endL, notPromoted);
                // UL stands for "Up Left" because the piece moves one square up and one left
                var endUL = new ChessPosition(row+1, col-1);
                var moveUL = new ChessMove(start, endUL, notPromoted);

                // Check if the king is near the top or bottom edge; then, check for the other two edges.
                if (row == 8) {
                    // King is at top edge
                    returnMoves.add(moveD);
                    // Check if king is near a right or left edge:
                    if (col == 8) {
                        // King is at right edge; top-right corner
                        returnMoves.add(moveDL);
                        returnMoves.add(moveL);
                    }
                    else if (col == 1) {
                        // King is at left edge; top-left corner
                        returnMoves.add(moveDR);
                        returnMoves.add(moveR);
                    }
                    else {
                        // King is at top edge, away from left and right edges.
                        returnMoves.add(moveDL);
                        returnMoves.add(moveL);
                        returnMoves.add(moveDR);
                        returnMoves.add(moveR);
                    }
                }
                else if (row == 1) {
                    // King is at bottom edge
                    returnMoves.add(moveU);
                    // Check if king is near a right or left edge:
                    if (col == 8) {
                        // King is at right edge; bottom-right corner
                        returnMoves.add(moveUL);
                        returnMoves.add(moveL);
                    }
                    else if (col == 1) {
                        // King is at left edge; bottom-left corner
                        returnMoves.add(moveUR);
                        returnMoves.add(moveR);
                    }
                    else {
                        // King is at bottom edge, away from left and right edges.
                        returnMoves.add(moveUL);
                        returnMoves.add(moveL);
                        returnMoves.add(moveUR);
                        returnMoves.add(moveR);
                    }
                }
                else {
                    // King is not at top or bottom edge
                    returnMoves.add(moveD);
                    returnMoves.add(moveU);
                    // Check if king is near a right or left edge:
                    if (col == 8) {
                        // King is at right edge
                        returnMoves.add(moveUL);
                        returnMoves.add(moveL);
                        returnMoves.add(moveDL);
                    }
                    else if (col == 1) {
                        // King is at left edge
                        returnMoves.add(moveUR);
                        returnMoves.add(moveR);
                        returnMoves.add(moveDR);
                    }
                    else {
                        // King is somewhere in the middle of the board, not at any edge
                        returnMoves.add(moveUL);
                        returnMoves.add(moveL);
                        returnMoves.add(moveDL);

                        returnMoves.add(moveUR);
                        returnMoves.add(moveR);
                        returnMoves.add(moveDR);
                    }
                }



                // Check if king is near a corner:

            }
            case QUEEN -> {
                // All diagonal and orthogonal movements
            }
            case BISHOP -> {
                // All diagonal movements
            }
            case KNIGHT -> {
                // Any movement going 2 squares in one direction, and 1 in a perpendicular direction
            }
            case ROOK -> {
                // All orthogonal movements
            }
            case PAWN -> {
                // Up two squares if starting; otherwise up one square.
                // Later, diagonal-up if there is a piece in front. Not sure how to check for that.
            }
        }
        return returnMoves;
    }

    public int HashCode() {
        return 0;
    }
}
