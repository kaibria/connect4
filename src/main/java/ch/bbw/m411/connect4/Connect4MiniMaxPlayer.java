package ch.bbw.m411.connect4;

import ch.bbw.m411.connect4.Connect4ArenaMain;

import java.util.ArrayList;
import static ch.bbw.m411.connect4.Connect4ArenaMain.*;

public class Connect4MiniMaxPlayer extends Connect4ArenaMain.DefaultPlayer {
    int bestMove = NOMOVE; // bestMove ist die beste Zugposition, die durch die MiniMax-Suche gefunden wird
    int maxDepth; // maxDepth ist die maximale Tiefe der MiniMax-Suche
    int minimalDepth; // minimalDepth ist die minimale Tiefe der MiniMax-Suche

    public Connect4MiniMaxPlayer(int depth) {
        super();
        maxDepth = depth;
    }

    @Override
    int play() {
        int movesAvailable = countAvailableMoves(board);
        minimalDepth = Math.min(movesAvailable, maxDepth); // minimalDepth ist die minimale der verfügbaren Züge und der maximalen Tiefe
        miniMaxPlay(myColor, minimalDepth); // Startet die MiniMax-Suche mit minimalDepth Tiefe
        return bestMove; // Gibt die beste gefundene Zugposition zurück
    }

    private int miniMaxPlay(Connect4ArenaMain.Stone myColor, int depth) {
        if (isWinning(board, myColor.opponent())) {
            return Integer.MIN_VALUE + 1; // Wenn der Gegner gewonnen hat, wird ein sehr niedriger Wert zurückgegeben
        }

        if (depth == 0) {
            return rate(myColor, board); // Wenn die maximale Tiefe erreicht ist, wird der aktuelle Zustand bewertet
        }

        if (myColor == this.myColor) {  // Wenn es der eigene Zug ist
            int max = Integer.MIN_VALUE;
            for (int move : getPossibleMoves(board)) {
                board[move] = myColor; // Zug wird ausgeführt
                int currentValue = miniMaxPlay(myColor.opponent(), depth - 1);
                board[move] = null; // Zug wird rückgängig
                if (depth == minimalDepth) {
                    System.out.println("Index: " + move + " Value: " + currentValue + "\n");
                }
                if (currentValue > max) {
                    max = currentValue;
                    if (depth == minimalDepth) {
                        bestMove = move; // Speichert den aktuellen Zug als besten Zug
                    }
                }
            }
            return max;
        } else { // Wenn es der Zug des Gegners ist
            int min = Integer.MAX_VALUE;
            for (int move : getPossibleMoves(board)) {
                board[move] = myColor; // Zug wird ausgeführt
                int currentValue = miniMaxPlay(myColor.opponent(), depth - 1);
                board[move] = null;
                if (depth == minimalDepth) {
                    System.out.println("Index: " + move + " Value: " + currentValue + "\n");
                }
                if (currentValue < min) {
                    min = currentValue;
                }
            }
            return min;
        }
    }

}
