package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {


    private TeamColor turn = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();

    public ChessGame() {
        // Board won't set itself in its constructor...
        board.resetBoard();
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
        // Get piece and initialize stuff
        ChessPiece piece = getBoard().getPiece(startPosition);
        TeamColor color = piece.getTeamColor();
        ArrayList<ChessMove> returnedMoves; // = (ArrayList<ChessMove>) piece.pieceMoves(board, startPosition);
        if (piece != null) {
            returnedMoves = (ArrayList<ChessMove>) piece.pieceMoves(board, startPosition);
        }
        else {
            return new ArrayList<ChessMove>();
        }

        // Remove invalid moves
        // Loop through entire thing. Make every move.
        // For EVERY SINGLE MOVE! create a copy of the board.
        ChessMove move = null;
        System.out.println(String.format("First move: %s",move));
        boolean first = true;
        boolean removeFirst = false;
        for (Iterator<ChessMove> moves = returnedMoves.iterator(); moves.hasNext();) {
            move = moves.next();

            System.out.println(String.format("Pdebug l_74, vMoves/CG, current move: %s", move));
            try {

                ChessGame testGame = (ChessGame) clone();
                testGame.setTeamTurn(color);
                testGame.setBoard(testGame.makeMoveForced(testGame.board, move));
                boolean checked = testGame.isInCheck(testGame.getTeamTurn());
                if (checked) {
                    //System.out.println("Print debugging, move results in check. Line 87, validMoves, ChessGame");
                    //ChessMove tmpMove = moves.next();
                    //System.out.println(String.format("Removing move %s",move));
                    moves.remove();
                }
            }
            catch (InvalidMoveException e) {
                System.out.println("Invalid move in makeMoveForced...off board.");
            }
            catch (Exception e) {
                System.out.println("Pdebug l_95, vMoves/CG. Cloning error?");
            }

        }

        return returnedMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece;
        if (move.getPromotionPiece() == null)
            piece = board.getPiece(startPosition);
        else
            piece = new ChessPiece(turn, move.getPromotionPiece());

        if (piece == null)
            throw new InvalidMoveException("No piece at start");
        if (piece.getTeamColor() != turn)
            throw new InvalidMoveException("Not the piece's turn!");
        Collection<ChessMove> validMoves;

        // Add in the moves
        validMoves = validMoves(startPosition);

//        catch (InvalidMoveException e) {
//            throw new InvalidMoveException("makeMove error: likely that board clone failed");
//        }

        // Check validity
        boolean valid;
        try {
            valid = validMoves.contains(move);
        }
        catch (Error e) {
            System.out.println("uh oh. Line 133 print debugging ChessGame");
        }
        if (validMoves.contains(move)) {
            // Make the move
            // Remove the piece that moves
            board.addPiece(startPosition, null);

            // Replace the piece that's getting taken
            // ...if not null at endPosition, maybe save the taken piece?
            board.addPiece(endPosition,piece);

            // Set new color for turn
            if (turn == TeamColor.WHITE)
                turn = TeamColor.BLACK;
            else
                turn = TeamColor.WHITE;
        }
        else {
            throw new InvalidMoveException(String.format("Invalid move: %s",move));
        }
    }

    /**
     * Makes a move, as long as the piece is on the board.
     * Used in validMoves to see if a move puts the player in check.
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is off the board
     */
    public ChessBoard makeMoveForced(ChessBoard board, ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(startPosition);

        boolean onBoard = true;
        int startrow = startPosition.getRow();
        int startcol = startPosition.getColumn();
        int endrow = endPosition.getRow();
        int endcol = endPosition.getColumn();
        if (startrow < 1 || startrow > 8 || startcol < 1 || startcol > 8) {
            onBoard = false;
        }
        if (endrow < 1 || endrow > 8 || endcol < 1 || endcol > 8) {
            onBoard = false;
        }
        if (onBoard) {
            // Make the move
            // Remove the piece that moves
            board.addPiece(startPosition, null);

            // Replace the piece that's getting taken
            // ...if not null at endPosition, maybe save the taken piece?
            board.addPiece(endPosition,piece);
            //System.out.println("Print Debugging, line 186 of ChessGame, makeMoveForced()");
        }
        else {
            throw new InvalidMoveException(String.format("Move is off board: %s",move));
        }

        // This returns the input board!! Not the game board.
        return board;
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Iterate and find king
        ChessPosition kingSpot = null;
        List<ChessMove> allmoves = new ArrayList<>();

        // Iterate through the chessboard.
        // Add all (potentially invalid) moves from other team.
        // Add king position
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPosition currPos = new ChessPosition(r,c);
                ChessPiece currPiece = board.getPiece(currPos);
                if (currPiece != null) {
                    if (currPiece.getTeamColor() != teamColor) {
                        allmoves.addAll(currPiece.pieceMoves(board, currPos));
                    } else {
                        // System.out.println(String.format("Print debugging, line 234, isInCheck; piece pos: %s",currPos));
                        if (currPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            kingSpot = currPos;
                        }
                    }
                }
            }
        }

        // Iterate through moves; find if any endPosition hits the king
        ChessMove move = allmoves.getFirst();
        //System.out.println(String.format("Print debugging isInCheck (line 244). King spot: %s",kingSpot));
        for (Iterator<ChessMove> moves = allmoves.iterator(); moves.hasNext();) {
            //System.out.println(move);
            if (move.getEndPosition().equals(kingSpot)) {
                // At least one piece has the king in sight.
                //System.out.println(String.format("Pdebug, line 267, loop 2 of isInCheck, ChessGame, %s",move));
                return true;
            }
            move = moves.next();
        }

        // Need to check that last move...I should really improve my loop.
        if (move.getEndPosition().equals(kingSpot)) {
            //System.out.println(String.format("Print debugging, line 280, lastmove of isInCheck, ChessGame, %s", move));
            return true;
        }


        // Never found a piece move that hits the king
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && isTrapped(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//        if (isInCheck(teamColor)) {
//            return false;
//        }

        return !isInCheck(teamColor) && isTrapped(teamColor);
    }

    /**
     * Helper function that both isInCheckmate and isInStalemate call.
     * Returns true if there are no valid moves for this color.
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isTrapped(TeamColor teamColor) {
        ArrayList<ChessMove> allmoves = new ArrayList<>();
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPosition pos = new ChessPosition(r,c);
                ChessPiece piece = board.getPiece(pos);
                if (piece != null) {
                    if (teamColor == piece.getTeamColor()) {
                        // Add new moves
                        allmoves.addAll(validMoves(pos));
                    }
                }
            }
        }
        if (allmoves.isEmpty()) {
            // No valid moves...
            return true;
        }

        // Has valid moves left
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ChessGame gameCopy = new ChessGame();
        gameCopy.setTeamTurn(turn);
        gameCopy.setBoard((ChessBoard) board.clone());

        return gameCopy;
    }
}
