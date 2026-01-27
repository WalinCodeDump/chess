package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
    default boolean positionIsValid(ChessBoard board, ChessPosition position, ChessPiece piece) {
        boolean isValid = true;
        // Within rows of chess board
        if (position.getRow() < 1 || position.getRow() > 8)
            isValid = false;

        // Within columns of chess board
        if (position.getColumn() < 1 || position.getColumn() > 8)
            isValid = false;
        // Create new position (will remove later if it collides)
        ChessPosition addpos = new ChessPosition(position.getRow(), position.getColumn());

        // Check if the piece is moving onto another piece
        if (board.getPiece(addpos) != null) {
            // Check if it's the same color or not
            if (board.getPiece(addpos).getTeamColor() == piece.getTeamColor())
                isValid = false;
        }
    }}
