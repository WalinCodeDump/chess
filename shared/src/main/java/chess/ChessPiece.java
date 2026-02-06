package chess;

import java.util.Collection;
import java.util.List;
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
        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()) {
            // Bishop moves
            case PieceType.BISHOP -> {
                BishopMovesCalculator bishop = new BishopMovesCalculator();
                return bishop.pieceMoves(board, myPosition);
            }
            // Rook moves
            case PieceType.ROOK -> {
                RookMovesCalculator rook = new RookMovesCalculator();
                return rook.pieceMoves(board, myPosition);
                // Testing...
            }
            // Queen moves
            case PieceType.QUEEN -> {
                QueenMovesCalculator queen = new QueenMovesCalculator();
                return queen.pieceMoves(board, myPosition);
            }
            // Knight moves
            case PieceType.KNIGHT -> {
                KnightMovesCalculator knight = new KnightMovesCalculator();
                return knight.pieceMoves(board, myPosition);
            }
            // Pawn moves
            case PieceType.PAWN -> {
                PawnMovesCalculator pawn = new PawnMovesCalculator();
                return pawn.pieceMoves(board, myPosition);
            }
            // King moves
            case PieceType.KING -> {
                KingMovesCalculator king = new KingMovesCalculator();
                return king.pieceMoves(board, myPosition);
            }
        }

        return List.of();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    protected ChessPiece clone() throws CloneNotSupportedException {

        return new ChessPiece(pieceColor, type);
    }
}
