package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // White on bottom.
        // King is to the right, if I forget.

        // Set pawns
        for (int i = 1; i <= 8; i++) {
            var whitePawnPos = new ChessPosition(2,i);
            var whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            addPiece(whitePawnPos, whitePawn);

            var blackPawnPos = new ChessPosition(7,i);
            var blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            addPiece(blackPawnPos, blackPawn);
        }
        // Set rooks
        {
            var whiteRookLPos = new ChessPosition(1,1);
            var whiteRookRPos = new ChessPosition(1,8);
            var whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
            addPiece(whiteRookLPos,whiteRook);
            addPiece(whiteRookRPos,whiteRook);

            var blackRookLPos = new ChessPosition(8,1);
            var blackRookRPos = new ChessPosition(8,8);
            var blackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
            addPiece(blackRookLPos,blackRook);
            addPiece(blackRookRPos,blackRook);
        }
        // Set knights
        {
            var whiteKnightLPos = new ChessPosition(1,2);
            var whiteKnightRPos = new ChessPosition(1,7);
            var whiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
            addPiece(whiteKnightLPos,whiteKnight);
            addPiece(whiteKnightRPos,whiteKnight);

            var blackKnightLPos = new ChessPosition(8,2);
            var blackKnightRPos = new ChessPosition(8,7);
            var blackKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
            addPiece(blackKnightLPos,blackKnight);
            addPiece(blackKnightRPos,blackKnight);
        }
        // Set bishops
        {
            var whiteBishopLPos = new ChessPosition(1,3);
            var whiteBishopRPos = new ChessPosition(1,6);
            var whiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
            addPiece(whiteBishopLPos,whiteBishop);
            addPiece(whiteBishopRPos,whiteBishop);

            var blackBishopLPos = new ChessPosition(8,3);
            var blackBishopRPos = new ChessPosition(8,6);
            var blackBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
            addPiece(blackBishopLPos,blackBishop);
            addPiece(blackBishopRPos,blackBishop);
        }
        // Set kings and queens
        {
            var whiteKingPos = new ChessPosition(1,5);
            var whiteQueenPos = new ChessPosition(1,4);
            var whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
            var whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
            addPiece(whiteKingPos,whiteKing);
            addPiece(whiteQueenPos,whiteQueen);

            var blackKingPos = new ChessPosition(8,5);
            var blackQueenPos = new ChessPosition(8,4);
            var blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
            var blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
            addPiece(blackKingPos,blackKing);
            addPiece(blackQueenPos,blackQueen);
        }
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.equals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(squares);
    }
}
