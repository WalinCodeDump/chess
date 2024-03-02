package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
                // Any valid position within 1 square of current place, including diagonals.

                PieceType notPromoted = null;
                // i loops through rows; j loops through columns. Values of i and j range from -1,0,1
                // since only adjacent squares are considered.
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j ==0) continue; // Can't move to its original position
                        if (row+i < 1 || row+i > 8) continue; // Can't move off the top or bottom edges
                        if (col+j < 1 || col+j > 8) continue; // Can't move off the left or right edges
                        var endSquare = new ChessPosition(row+i,col+j);
                        var move = new ChessMove(start, endSquare, notPromoted);
                        returnMoves.add(move);
                    }
                }
            }
            case QUEEN -> {
                // All diagonal and orthogonal movements

                PieceType notPromoted = null;

            }
            case BISHOP -> {
                // All diagonal movements

                PieceType notPromoted = null;
                // adds based on diagonal movements.
                // Adds up-right moves.
                for (int i = 1; i < 8; i++) {
                    if (row+i > 8 || col+i > 8) break; // Can't move off the top or right edges

                    var endUR = new ChessPosition(row+i,col+i);
                    var moveUR = new ChessMove(start, endUR, notPromoted);

                    // TODO: Find if a space is blocked (same color) or occupied (different color)
                    // If blocked: break. If occupied: add, then break.
                    returnMoves.add(moveUR);
                }
                // Adds down-right moves.
                for (int i = 1; i < 8; i++) {
                    if (row-i < 1 || col+i > 8) break; // Can't move off the bottom or right edges

                    var endDR = new ChessPosition(row-i,col+i);
                    var moveDR = new ChessMove(start, endDR, notPromoted);

                    // TODO: Find if a space is blocked (same color) or occupied (different color)
                    // If blocked: break. If occupied: add, then break.
                    returnMoves.add(moveDR);
                }
                // Adds down-left moves.
                for (int i = 1; i < 8; i++) {
                    if (row-i < 1 || col-i < 1) break; // Can't move off the bottom or left edges

                    var endDL = new ChessPosition(row-i,col-i);
                    var moveDL = new ChessMove(start, endDL, notPromoted);

                    // TODO: Find if a space is blocked (same color) or occupied (different color)
                    // If blocked: break. If occupied: add, then break.
                    returnMoves.add(moveDL);
                }
                // Adds up-left moves.
                for (int i = 1; i < 8; i++) {
                    if (row+i > 8 || col-i < 1) break; // Can't move off the top or left edges

                    var endUL = new ChessPosition(row+i,col-i);
                    var moveUL = new ChessMove(start, endUL, notPromoted);

                    // TODO: Find if a space is blocked (same color) or occupied (different color)
                    // If blocked: break. If occupied: add, then break.
                    returnMoves.add(moveUL);
                }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
