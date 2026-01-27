package chess;

import java.util.Collection;
import java.util.stream.Stream;

public class QueenMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Fairly simple. Just add both bishop and rook moves.
        BishopMovesCalculator bishop = new BishopMovesCalculator();
        Collection<ChessMove> bmoves = bishop.pieceMoves(board, position);
        RookMovesCalculator rook = new RookMovesCalculator();
        Collection<ChessMove> rmoves = rook.pieceMoves(board, position);

        // Hm. Just learned about Stream concatenation. Cool utility.
        return Stream.concat(bmoves.stream(),rmoves.stream()).toList();
    }
}
