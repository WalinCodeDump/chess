package chess;

import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        throw new RuntimeException("Not implemented");
        // Start writing...
        // 1 - Move around in all four directions.
        // 2 - Do checks
        // 2a - Is off board? Strike it.
        // 2b - Collides with own color? Strike it.
        // 2c - Is past another piece? Strike it.

        // So... - Iterate moving outwards first. Stop iterating after any of the above.
        // Actually, also stop iterating a direction after encountering opposite color.

        ChessPosition currpos = new ChessPosition(position.getRow(), position.getColumn());
        // Northwest
        for (int i = 1; i <= 8; i++) {
            // Check for collisions! With edge, with piece
            var a = 1; // Stubbed
        }
    }

}
