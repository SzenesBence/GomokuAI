package hu.nye.mi;

import java.util.Arrays;
import java.util.Scanner;

import static hu.nye.mi.Table.*;

public class AI {
    Table table = new Table();
//getscorral van a gond, rossz a heurisztikai érték

    public static int minimax(Table table, int depth, boolean isMax) {
        int boardVal = getScore(table);
        if (table.checkWin('X')) return 10000;  // AI wins
        if (table.checkWin('O')) return -10000; // Player wins
        if ( depth == 0 || table.anyMovesAvailable()) {
            return boardVal;
        }

        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (table.isAvailable(row, col)) {
                        board[row][col] = 'X';
                        int value = minimax(table, depth - 1, false);
                        board[row][col] = '-';
                        highestVal = Math.max(highestVal, value);
                    }
                }
            }
            return highestVal;
        }
        // Minimising player (opponent's turn)
        else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (table.isAvailable(row, col)) {
                        board[row][col] = 'O'; // Try opponent move
                        int value = minimax(table, depth - 1, true);
                        board[row][col] = '-'; // Undo move
                        lowestVal = Math.min(lowestVal, value);
                    }
                }
            }
            return lowestVal;
        }
    }

    public static int[] getBestMove(Table table) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (table.isAvailable(row, col)) {
                    board[row][col] = 'X'; // Try move
                    int moveValue = minimax(table, 6, false); // Evaluate
                    board[row][col] = '-'; // Undo move
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }

    public void setX() {
        int[] move = getBestMove(table);
        if (move[0] != -1 && move[1] != -1) {
            board[move[0]][move[1]] = 'X';
        }
        if(checkWin('X')){
            System.out.println("X WINS!");
        Menu menu=new Menu();
        menu.closeScanner();
        System.exit(0);
        }
    }




    public static int getScore(Table table){
    char AIchar = 'X';
    char Playerchar='O';


 int heuristicScore=0;

    for (int row = 0; row < SIZE; row++) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == AIchar) {
                for (int[] dir : table.directions) {
                    int count = table.countConsecutive(row, col, dir[0], dir[1], AIchar);

                    if (count == 4) {
                        heuristicScore += 500;
                    } else if (count == 3) {
                        heuristicScore += 100;
                    } else if (count == 2) {
                        heuristicScore += 30;
                    } else if (count == 1) {
                       heuristicScore += 10;
                    }

                }
            }
            if (board[row][col] == Playerchar) {
                for (int[] dir : table.directions) {
                    int count = table.countConsecutive(row, col, dir[0], dir[1], Playerchar);
                    if (count == 4) {
                        heuristicScore -= 500;
                    } else if (count == 3) {
                       heuristicScore -= 100;
                    } else if (count == 2) {
                        heuristicScore -= 30;
                    } else if (count == 1) {
                        heuristicScore -= 10;
                    }

                }
            }
        }
    }
    return heuristicScore;

}
/*

    public void blockPlayer() {
        char player = 'O';

        for (int row = 0; row < Table.SIZE; row++) {
            for (int col = 0; col < Table.SIZE; col++) {
                if (board[row][col] == player) {
                    for (int[] dir : table.directions) {
                        int count = table.countConsecutive(row, col, dir[0], dir[1], player);

                        if (count >= 2) {

                            int endRow1 = row + dir[0] * (count + 1);
                            int endCol1 = col + dir[1] * (count + 1);

                            int endRow2 = row - dir[0];
                            int endCol2 = col - dir[1];

                            if (table.isInBounds(endRow1, endCol1) && table.isAvailable(endRow1, endCol1)) {
                                board[endRow1][endCol1] = 'X';
                                return;
                            }

                            if (table.isInBounds(endRow2, endCol2) && table.isAvailable(endRow2, endCol2)) {
                                board[endRow2][endCol2] = 'X';
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    */
    public final void aiFirstMove(int row, int col){
        int min=-1;
        int max=1;
        int r1 ;
        int r2;

        r1 = min + (int) (Math.random() * ((max - min) + 1));
        r2 = min + (int) (Math.random() * ((max - min) + 1));

        row += r1;
        col += r2;

        System.out.println(row+","+col);
        if(table.isAvailable(row,col)&&row!=0&&col!=0){
            board[row][col]='X';
        }else return;


    }




}

