package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class Connect4AlphaBetaPlayer extends Connect4ArenaMain.DefaultPlayer {

    int bestMove = NOMOVE; // bestMove ist die beste Zugposition, die durch die Alpha-Beta-Suche gefunden wird
    int maxSearchDepth; // maxSearchDepth ist die maximale Tiefe der Alpha-Beta-Suche
    int minSearchDepth; // minSearchDepth ist die minimale Tiefe der Alpha-Beta-Suche

    public Connect4AlphaBetaPlayer(int depth) {
        super();
        maxSearchDepth = depth;
    }

    @Override
    int play() {
        minSearchDepth = Math.min(countAvailableMoves(board), maxSearchDepth); // minSearchDepth ist die minimale der verfügbaren Züge und der maximalen Tiefe
        alphaBetaPlay(myColor, minSearchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return bestMove;
    }

    private int alphaBetaPlay(Connect4ArenaMain.Stone myColor, int depth, int alpha, int beta) {
        if (isWinning(board, myColor.opponent())) {
            return Integer.MIN_VALUE + 1; // Wenn der Gegner gewonnen hat, wird ein sehr niedriger Wert zurückgegeben
        }
        if (depth == 0) {
            return evaluate(myColor, board); // Wenn die maximale Tiefe erreicht ist, wird der aktuelle Zustand bewertet
        }
        int max = alpha;

        for (int move : getPossibleMoves(board)) {

            if (board[move] == null) {
                board[move] = myColor;

                int currentValue = -alphaBetaPlay(myColor.opponent(), depth - 1, -beta, -max);

                board[move] = null; // Der Zug wird rückgängig gemacht
                if (currentValue > max) {
                    max = currentValue;
                    if (depth == minSearchDepth) {
                        bestMove = move; // Speichert den aktuellen Zug als besten Zug
                    }
                    if (max >= beta) {
                        break; // Alpha-Beta-Pruning
                    }
                }
            }
        }
        return max;
    }
}
