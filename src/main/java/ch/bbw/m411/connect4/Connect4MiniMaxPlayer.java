package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class Connect4MiniMaxPlayer extends Connect4ArenaMain.DefaultPlayer {
    int bestMove = NOMOVE; // bestMove ist die beste Zugposition, die durch die MiniMax-Suche gefunden wird
    int maxSearchDepth; // maxSearchDepth ist die maximale Tiefe der MiniMax-Suche
    int minSearchDepth; // minimalSearchDepth ist die minimale Tiefe der MiniMax-Suche

    public Connect4MiniMaxPlayer(int depth) {
        super();
        maxSearchDepth = depth;
    }

    @Override
    int play() {
        minSearchDepth = Math.min(countAvailableMoves(board), maxSearchDepth); // minimalSearchDepth ist die minimale der verfügbaren Züge und der maximalen Tiefe
        miniMaxPlay(myColor, minSearchDepth); // Startet die MiniMax-Suche mit minimalSearchDepth Tiefe
        return bestMove; // Gibt die beste gefundene Zugposition zurück
    }

    private int miniMaxPlay(Connect4ArenaMain.Stone currentPlayer, int depth) {
        if (isWinning(board, currentPlayer.opponent())) {
            return Integer.MIN_VALUE + 1; // Wenn der Gegner gewonnen hat, wird ein sehr niedriger Wert zurückgegeben
        }

        if (depth == 0) {
            return evaluate(currentPlayer, board); // Wenn die maximale Tiefe erreicht ist, wird der aktuelle Zustand bewertet
        }

        if (currentPlayer == this.myColor) {  // Wenn es der eigene Zug ist
            int max = Integer.MIN_VALUE;
            for (int move : getPossibleMoves(board)) {
                board[move] = currentPlayer; // Zug wird ausgeführt
                int currentValue = miniMaxPlay(currentPlayer.opponent(), depth - 1);
                board[move] = null; // Zug wird rückgängig
                if (currentValue > max) {
                    max = currentValue;
                    if (depth == minSearchDepth) {
                        bestMove = move; // Speichert den aktuellen Zug als besten Zug
                    }
                }
            }
            return max;
        } else { // Wenn es der Zug des Gegners ist
            int min = Integer.MAX_VALUE;
            for (int move : getPossibleMoves(board)) {
                board[move] = currentPlayer.opponent(); // Zug wird ausgeführt
                int currentValue = miniMaxPlay(myColor, depth - 1);
                board[move] = null; // Zug wird rückgängig
                if (currentValue < min) {
                    min = currentValue;
                }
            }
            return min;
        }
    }


}
