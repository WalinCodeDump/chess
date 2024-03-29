package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor turn; // holds team's turn
    private ChessBoard theboard;

    public ChessGame() {
        turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // first, get all pieceMoves. Call each of these a myMove.
        var myPiece = theboard.getPiece(startPosition);
        var myMoves = myPiece.pieceMoves(theboard,startPosition);
        var returnMoves = myMoves;
        for (ChessMove myMove: myMoves) {
            // Make each myMove individually and then
            // check the corresponding set of all pieceMoves of the opposing team.
            // (i.e. run isInCheck)

            // Make the myMove (save the piece, if any, at the end Position so the move is reversible)
            var startPos = myMove.getStartPosition();
            var endPos = myMove.getEndPosition();
            var endPiece = theboard.getPiece(endPos);
            theboard.addPiece(startPos,null);
            theboard.addPiece(endPos,myPiece);

            // Now, see if the king is in check.
            var inCheck = isInCheck(myPiece.getTeamColor());

            // If one of the opposing pieceMoves includes the king's position, then the myMove is not Valid.
            // TODO: Make sure that removing a ChessMove from the "myMoves" set does not mess up the for loop
            if (inCheck) {
                myMoves.remove(myMove);
            }

            // Reverse the myMove to get ready for the next one.
            theboard.addPiece(endPos, endPiece);
            theboard.addPiece(startPos,myPiece);
        }

        return myMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // get the start and end positions and the piece that's at the start

        // Check that the move is in the validMoves set for the piece

        // addPiece the piece to the final position

        // addPiece null to the start position

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // cycle through the pieceMoves for the other side.
        // if any of them include the king's current position, return true.

        ChessPosition kingPos = null; // We'll find out where the king's position is later
        var isCheck = false; // Default to false.

        // Find the king (this could be contained in the other loop, but then we'd have to save the full set
        // of pieceMoves for every piece on the opponent's side. This is easier and saves on memory.)
        for (int row = 1; row <= 8; row++) {
            if (kingPos != null) break; // If it's found by now, then break out of the loop.
            for (int col = 1; col <= 8; col++) {
                var currPos = new ChessPosition(row, col);
                var currPiece = theboard.getPiece(currPos);
                if (currPiece.getPieceType() == ChessPiece.PieceType.KING && currPiece.getTeamColor() == teamColor) {
                    kingPos = new ChessPosition(row, col); // Not sure if setting it equal to currPos causes problems
                    break;
                }
            }
        }

        // Cycle through each of the squares
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                // On each square:
                // - save the position and piece
                // - get the team color; if it's the opposite side, then run pieceMoves
                // - if the validMoves set contains a move that targets the king, break and return true.
                var currPos = new ChessPosition(row, col);
                var currPiece = theboard.getPiece(currPos);
                if (currPiece.getTeamColor() != teamColor) {
                    var otherTeamMoves = currPiece.pieceMoves(theboard,currPos);
                    for (var oMove : otherTeamMoves) {
                        if (oMove.getEndPosition() == kingPos) {
                            isCheck = true;
                            return true; // At least one piece of the opposition is attacking the king currently
                        }
                    }
                }
            }
        }

        return isCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
