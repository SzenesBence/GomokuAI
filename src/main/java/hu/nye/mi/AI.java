package hu.nye.mi;

import static hu.nye.mi.Table.*;

public class AI {
    Table table = new Table();

//getscorral van a gond, rossz a heurisztikai érték

    public static int minimax(Table table, int depth,int alpha, int beta, boolean isMax) {

        int boardVal = getScore();

        if (checkWin('X')) return 10000;  // AI wins
        if (checkWin('O')) return -10000; // Player wins
        if (Math.abs(boardVal) >= 100000 || depth == 0 || !table.anyMovesAvailable()) {
            return boardVal;
        }
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (table.isAvailable(row, col)) {
                        board[row][col] = 'X';
                         highestVal =Math.max(highestVal,minimax(table,depth-1,alpha,beta,false));
                        board[row][col] = '-';
                        alpha=Math.max(alpha,highestVal);
                        if (alpha>=beta){
                            return highestVal;
                        }

                    }
                }
            }
            return highestVal;
        }

        else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (table.isAvailable(row, col)) {
                        board[row][col] = 'O'; // Try opponent move
                        lowestVal=Math.min(lowestVal,minimax(table,depth-1,alpha,beta,true));
                        board[row][col] = '-'; // Undo move
                        beta=Math.min(beta,lowestVal);
                        if (beta<=alpha){
                            return lowestVal;
                        }
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
                    int moveValue = minimax(table, 3,Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
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
        menu.endGame();

        }
    }




    public static int getScore() {
        // Immediate win/loss checks
        if (checkWin('X')) return 1000000;  // AI wins
        if (checkWin('O')) return -1000000; // Player wins

        int score = 0;

        // Evaluate all positions
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Evaluate X opportunities
                if (board[row][col] == 'X') {
                    for (int[] dir : directions) {
                        int count = countConsecutive(row, col, dir[0], dir[1], 'X');
                        if (count >= 4) score += 100000;  // Immediate win
                        else if (count == 3) score += 10000; // Strong threat
                        else if (count == 2) score += 100;   // Potential
                    }
                }
                // Evaluate O threats
                else if (board[row][col] == 'O') {
                    for (int[] dir : directions) {
                        int count = countConsecutive(row, col, dir[0], dir[1], 'O');
                        if (count >= 4) score -= 200000;  // Must block!
                        else if (count == 3) score -= 50000; // Critical block
                        else if (count == 2) score -= 1000;  // Developing threat
                    }
                }
            }
        }
        return score;
    }

/*
    public void blockPlayer() {
        char player = 'O';

        for (int row = 0; row < Table.SIZE; row++) {
            for (int col = 0; col < Table.SIZE; col++) {
                if (board[row][col] == player) {
                    for (int[] dir : directions) {
                        int count = countConsecutive(row, col, dir[0], dir[1], player);

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




}

