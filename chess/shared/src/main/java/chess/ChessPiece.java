package chess;

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

                        // Create move.
                        var endSquare = new ChessPosition(row+i,col+j);
                        var move = new ChessMove(start, endSquare, notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endSquare) == null) {
                            // Space is unoccupied.
                            returnMoves.add(move);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,move)) {
                                // Space contains an enemy piece.
                                returnMoves.add(move);
                            }
                            else {
                                // Space contains a friendly piece.
                            }
                        }
                    }
                }
            }
            case QUEEN -> {
                // All diagonal and orthogonal movements

                // All diagonal movements (cribbed from Bishop section)
                // I get that this should be abstracted elsewhere rather than repeated, but...
                // I don't want to create any new methods just to end up effectively copying them in anyway.
                {
                    PieceType notPromoted = null;

                    // Adds up-right moves.
                    for (int i = 1; i < 8; i++) {
                        if (row+i > 8 || col+i > 8) break; // Can't move off the top or right edges

                        var endUR = new ChessPosition(row+i,col+i);
                        var moveUR = new ChessMove(start, endUR, notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endUR) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveUR);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveUR)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveUR);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Adds down-right moves.
                    for (int i = 1; i < 8; i++) {
                        if (row-i < 1 || col+i > 8) break; // Can't move off the bottom or right edges

                        var endDR = new ChessPosition(row-i,col+i);
                        var moveDR = new ChessMove(start, endDR, notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endDR) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveDR);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveDR)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveDR);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Adds down-left moves.
                    for (int i = 1; i < 8; i++) {
                        if (row-i < 1 || col-i < 1) break; // Can't move off the bottom or left edges

                        var endDL = new ChessPosition(row-i,col-i);
                        var moveDL = new ChessMove(start, endDL, notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endDL) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveDL);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveDL)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveDL);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Adds up-left moves.
                    for (int i = 1; i < 8; i++) {
                        if (row+i > 8 || col-i < 1) break; // Can't move off the top or left edges

                        var endUL = new ChessPosition(row+i,col-i);
                        var moveUL = new ChessMove(start, endUL, notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endUL) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveUL);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveUL)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveUL);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                }

                // All orthogonal movements (cribbed from Rook section)
                // I get that this should be abstracted elsewhere rather than repeated, but...
                // I don't want to create any new methods just to end up effectively copying them in anyway.
                {
                    // All orthogonal movements
                    PieceType notPromoted = null;

                    // Moves upward
                    for (int i = 1; i <= 8; i++) {
                        if (row + i > 8) break; // Can't move up past the top row
                        var endU = new ChessPosition(row+i,col);
                        var moveU = new ChessMove(start,endU,notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endU) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveU);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveU)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveU);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Moves downward
                    for (int i = 1; i <= 8; i++) {
                        if (row - i < 1) break; // Can't move down past the bottom row
                        var endD = new ChessPosition(row-i,col);
                        var moveD = new ChessMove(start,endD,notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endD) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveD);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveD)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveD);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Moves left
                    for (int i = 1; i <= 8; i++) {
                        if (col - i < 1) break; // Can't move left past the left column
                        var endL = new ChessPosition(row,col-i);
                        var moveL = new ChessMove(start,endL,notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endL) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveL);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveL)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveL);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                    // Moves right
                    for (int i = 1; i <= 8; i++) {
                        if (col + i > 8) break; // Can't move right past the right column
                        var endR = new ChessPosition(row,col+i);
                        var moveR = new ChessMove(start,endR,notPromoted);

                        // Find if a space is blocked (same color) or occupied (different color)
                        if (board.getPiece(endR) == null) {
                            // Space is unoccupied.
                            returnMoves.add(moveR);
                        }
                        else {
                            // Space is occupied.
                            if (isUnblocked(board,moveR)) {
                                // Space contains an enemy piece.
                                // The move is valid, but the queen cannot move past this piece.
                                returnMoves.add(moveR);
                                break;
                            }
                            else {
                                // Space contains a friendly piece. Cannot move to or past here.
                                break;
                            }
                        }
                    }
                }
            }
            case BISHOP -> {
                // All diagonal movements

                PieceType notPromoted = null;

                // Adds up-right moves.
                for (int i = 1; i < 8; i++) {
                    if (row+i > 8 || col+i > 8) break; // Can't move off the top or right edges

                    var endUR = new ChessPosition(row+i,col+i);
                    var moveUR = new ChessMove(start, endUR, notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endUR) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveUR);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveUR)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the bishop cannot move past this piece.
                            returnMoves.add(moveUR);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Adds down-right moves.
                for (int i = 1; i < 8; i++) {
                    if (row-i < 1 || col+i > 8) break; // Can't move off the bottom or right edges

                    var endDR = new ChessPosition(row-i,col+i);
                    var moveDR = new ChessMove(start, endDR, notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endDR) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveDR);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveDR)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the bishop cannot move past this piece.
                            returnMoves.add(moveDR);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Adds down-left moves.
                for (int i = 1; i < 8; i++) {
                    if (row-i < 1 || col-i < 1) break; // Can't move off the bottom or left edges

                    var endDL = new ChessPosition(row-i,col-i);
                    var moveDL = new ChessMove(start, endDL, notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endDL) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveDL);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveDL)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the bishop cannot move past this piece.
                            returnMoves.add(moveDL);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Adds up-left moves.
                for (int i = 1; i < 8; i++) {
                    if (row+i > 8 || col-i < 1) break; // Can't move off the top or left edges

                    var endUL = new ChessPosition(row+i,col-i);
                    var moveUL = new ChessMove(start, endUL, notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endUL) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveUL);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveUL)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the bishop cannot move past this piece.
                            returnMoves.add(moveUL);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
            }
            case KNIGHT -> {
                // Any movement going 2 squares in one direction, and 1 in a perpendicular direction
                // There are eight locations to move to. Two for each orthogonal direction.
                PieceType notPromoted = null; // unused

                // Up: UL, UR    (up 2, then 1 to a side)
                ChessMove moveUL = knightMove(row,col,2,-1);
                ChessMove moveUR = knightMove(row,col,2,1);
                if (row + 2 <= 8) {
                    // Bounds and occupation check for Up-Left
                    if (col - 1 >= 1 && isUnblocked(board,moveUL)) {
                        returnMoves.add(moveUL);
                    }
                    // Bounds and occupation check for Up-Right
                    if (col + 1 <= 8 && isUnblocked(board,moveUR)) {
                        returnMoves.add(moveUR);
                    }
                }

                // Right: RU, RD (right 2, then 1 to a side)
                ChessMove moveRU = knightMove(row,col,1,2);
                ChessMove moveRD = knightMove(row,col,-1,2);
                if (col + 2 <= 8) {
                    // Bounds and occupation check for Right-Up
                    if (row + 1 <= 8 && isUnblocked(board,moveRU)) {
                        returnMoves.add(moveRU);
                    }
                    // Bounds and occupation check for Right-Down
                    if (row - 1 >= 1 && isUnblocked(board,moveRD)) {
                        returnMoves.add(moveRD);
                    }
                }

                // Down: DR, DL  (down 2, then 1 to a side)
                ChessMove moveDR = knightMove(row,col,-2,1);
                ChessMove moveDL = knightMove(row,col,-2,-1);
                if (row - 2 >= 1) {
                    // Bounds and occupation check for Down-Right
                    if (col +1 <= 8 && isUnblocked(board,moveDR)) {
                        returnMoves.add(moveDR);
                    }
                    // Bounds and occupation check for Down-Left
                    if (col - 1 >= 1 && isUnblocked(board,moveDL)) {
                        returnMoves.add(moveDL);
                    }
                }

                // Left: LD, LU  (left 2, then 1 to a side)
                ChessMove moveLD = knightMove(row,col,-1,-2);
                ChessMove moveLU = knightMove(row,col,1,-2);
                if (col - 2 >= 1) {
                    // Bounds and occupation check for Left-Down
                    if (row - 1 >= 1 && isUnblocked(board,moveLD)) {
                        returnMoves.add(moveLD);
                    }
                    // Bounds and occupation check for Left-Up
                    if (row + 1 <= 8 && isUnblocked(board,moveLU)) {
                        returnMoves.add(moveLU);
                    }
                }

                //TODO: Add the moves
                //TODO: Add a block-checker.
            }
            case ROOK -> {
                // All orthogonal movements
                PieceType notPromoted = null;

                // Moves upward
                for (int i = 1; i <= 8; i++) {
                    if (row + i > 8) break; // Can't move up past the top row
                    var endU = new ChessPosition(row+i,col);
                    var moveU = new ChessMove(start,endU,notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endU) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveU);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveU)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the rook cannot move past this piece.
                            returnMoves.add(moveU);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Moves downward
                for (int i = 1; i <= 8; i++) {
                    if (row - i < 1) break; // Can't move down past the bottom row
                    var endD = new ChessPosition(row-i,col);
                    var moveD = new ChessMove(start,endD,notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endD) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveD);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveD)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the rook cannot move past this piece.
                            returnMoves.add(moveD);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Moves left
                for (int i = 1; i <= 8; i++) {
                    if (col - i < 1) break; // Can't move left past the left column
                    var endL = new ChessPosition(row,col-i);
                    var moveL = new ChessMove(start,endL,notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endL) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveL);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveL)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the rook cannot move past this piece.
                            returnMoves.add(moveL);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
                // Moves right
                for (int i = 1; i <= 8; i++) {
                    if (col + i > 8) break; // Can't move right past the right column
                    var endR = new ChessPosition(row,col+i);
                    var moveR = new ChessMove(start,endR,notPromoted);

                    // Find if a space is blocked (same color) or occupied (different color)
                    if (board.getPiece(endR) == null) {
                        // Space is unoccupied.
                        returnMoves.add(moveR);
                    }
                    else {
                        // Space is occupied.
                        if (isUnblocked(board,moveR)) {
                            // Space contains an enemy piece.
                            // The move is valid, but the rook cannot move past this piece.
                            returnMoves.add(moveR);
                            break;
                        }
                        else {
                            // Space contains a friendly piece. Cannot move to or past here.
                            break;
                        }
                    }
                }
            }
            case PAWN -> {
                PieceType promotionPiece = null;

                // It goes up if white, and down if black. Therefore, here is a switch value.
                int vertstep; // "Vertical step" value; set in braces below.
                {
                    if (pieceColor == ChessGame.TeamColor.WHITE) {
                        // White pawn
                        vertstep = 1;
                    } else {
                        // Black pawn
                        vertstep = -1;
                    }
                }
                // And now, non-promoting moves.
                if (row + vertstep > 1 && row + vertstep < 8)
                {
                    // Diagonals
                    {
                        // Diagonal-left
                        if (col - 1 >= 1) {
                            var endDiagL = new ChessPosition(row + vertstep, col - 1);
                            var moveDiagL = new ChessMove(start, endDiagL, promotionPiece);
                            if (isUnblocked(board,moveDiagL) && board.getPiece(endDiagL) != null) {
                                // Taking a piece
                                returnMoves.add(moveDiagL);
                            }
                        }
                        // Diagonal-right
                        if (col + 1 <= 8) {
                            var endDiagR = new ChessPosition(row + vertstep, col + 1);
                            var moveDiagR = new ChessMove(start, endDiagR, promotionPiece);
                            if (isUnblocked(board,moveDiagR) && board.getPiece(endDiagR) != null) {
                                // Taking a piece
                                returnMoves.add(moveDiagR);
                            }
                        }
                    }
                    // Steps forward
                    {
                        // One step forward
                        var endOne = new ChessPosition(row + vertstep, col);
                        var moveOne = new ChessMove(start,endOne,promotionPiece);
                        if (isUnblocked(board, moveOne) && board.getPiece(endOne) == null) {
                            returnMoves.add(moveOne);

                            // Two steps forward
                            // NOTE: the "one step forward" move has to be unoccupied in order to check
                            // if it's possible to add a "two steps forward" move.
                            boolean blackStart = (pieceColor == ChessGame.TeamColor.BLACK && row == 7);
                            boolean whiteStart = (pieceColor == ChessGame.TeamColor.WHITE && row == 2);
                            if (blackStart || whiteStart) {
                                var endTwo = new ChessPosition(row + 2 * vertstep, col);
                                var moveTwo = new ChessMove(start, endTwo, null);
                                if (isUnblocked(board, moveTwo) && board.getPiece(endTwo) == null) {
                                    returnMoves.add(moveTwo);
                                }
                            }
                        }

                    }
                }
                // Then, promoting moves (if black: pawn moves from row 2. If white: pawn moves from row 7)
                else if (row + vertstep >= 1 && row + vertstep <= 8){
                    // All piece types except for King and Pawn are included and looped through.
                    var promotions = new PieceType[] {PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT, PieceType.QUEEN};
                    for (int i = 0; i < promotions.length; i++) {
                        promotionPiece = promotions[i];
                        // Diagonals
                        {
                            // Diagonal-left
                            if (col - 1 >= 1) {
                                var endDiagL = new ChessPosition(row + vertstep, col - 1);
                                var moveDiagL = new ChessMove(start, endDiagL, promotionPiece);
                                if (isUnblocked(board, moveDiagL) && board.getPiece(endDiagL) != null) {
                                    // Taking a piece
                                    returnMoves.add(moveDiagL);
                                }
                            }
                            // Diagonal-right
                            if (col + 1 <= 8) {
                                var endDiagR = new ChessPosition(row + vertstep, col + 1);
                                var moveDiagR = new ChessMove(start, endDiagR, promotionPiece);
                                if (isUnblocked(board, moveDiagR) && board.getPiece(endDiagR) != null) {
                                    // Taking a piece
                                    returnMoves.add(moveDiagR);
                                }
                            }
                        }
                        // One step forward
                        // In this branch, there is no "two steps forward" move.
                        {
                            var endOne = new ChessPosition(row + vertstep, col);
                            var moveOne = new ChessMove(start, endOne, promotionPiece);
                            if (isUnblocked(board, moveOne) && board.getPiece(endOne) == null) {
                                // Not taking a piece
                                returnMoves.add(moveOne);
                            }
                        }
                    }
                }
            }
        }
        return returnMoves;
    }


    /**
     * Abstracts the movement for a knight piece. Generates a new ChessMove that is
     * 2 squares over in an orthogonal direction to the piece, and 1 square over in
     * a perpendicular direction to the 2-square step.
     *
     * @param row: The starting row of the knight piece.
     * @param col: The starting column of the knight piece.
     * @param updown The movement in the up or down direction. Positive for up; negative for down.
     * @param leftright The movement in the left or right direction. Positive for right; negative for left.
     * @return A move from (row,col) to (row+updown,col+leftright)
     */
    public ChessMove knightMove(int row, int col, int updown, int leftright)
    {
        // Bounds check
        if (row + updown < 1 || row + updown > 8) return null;
        if (col + leftright < 1 || col + leftright > 8) return null;


        var start = new ChessPosition(row,col);
        var end = new ChessPosition(row + updown, col + leftright);
        // TODO: Add piece check.
        // Use ChessBoard.getPiece to check team color as well as the opponents' team color.

        // Then, return the actual move if it's valid.
        return new ChessMove(start,end,null);
    }

    /**
     * Checks if a move is blocked by other pieces, either its own color or other colors.
     *
     * @param board the board that the move is being made on.
     * @param move the move.
     * @return true if that move can be made (i.e. the move is unblocked).
     */
    public boolean isUnblocked(ChessBoard board, ChessMove move)
    {
        var start = move.getStartPosition();
        var startPiece = board.getPiece(start);
        var startColor = startPiece.getTeamColor();

        var end = move.getEndPosition();
        var endPiece = board.getPiece(end);
        if (endPiece == null) return true;
        var endColor = endPiece.getTeamColor();
        if (startColor == endColor) return false;

        // Is going to take a piece
        return true;
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
