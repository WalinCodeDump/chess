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
        ChessPiece piece = getBoard().getPiece(startPosition);
        TeamColor color = piece.getTeamColor();

        Collection<ChessMove> returnedMoves = piece.pieceMoves(board,startPosition);

        // TODO: remove invalid moves!!
        // Loop through entire thing. Make every move.
        // For EVERY SINGLE MOVE! create a copy of the board.

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
        ChessPiece piece = board.getPiece(startPosition);

        Collection<ChessMove> validMoves = validMoves(startPosition);
        if (validMoves.contains(move)) {
            // Make the move
            // Remove the piece that moves
            board.addPiece(startPosition, null);

            // Replace the piece that's getting taken
            // ...if not null at endPosition, maybe save the taken piece?
            board.addPiece(endPosition,piece);
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
    public void makeMoveForced(ChessMove move) throws InvalidMoveException {
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
        }
        else {
            throw new InvalidMoveException(String.format("Move is off board: %s",move));
        }
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
                        if (currPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            kingSpot = currPos;
                        }
                    }
                }
            }
        }

        // Iterate through moves; find if anyendPosition hits the king
        ChessMove move = allmoves.getFirst();
        for (Iterator<ChessMove> moves = allmoves.iterator(); moves.hasNext();) {
            if (move.getEndPosition().equals(kingSpot)) {
                // At least one piece has the king in sight.
                return true;
            }
            move = moves.next();
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
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
}
