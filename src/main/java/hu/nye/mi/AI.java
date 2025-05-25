
package hu.nye.mi;

import static hu.nye.mi.Table.*;

public class AI {
    Table table = new Table();


    public static int minimax(Table table, int depth,int alpha, int beta, boolean isMax) {

        int boardVal = getScore();


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
                        board[row][col] = 'O';
                        lowestVal=Math.min(lowestVal,minimax(table,depth-1,alpha,beta,true));
                        board[row][col] = '-';
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
                    board[row][col] = 'X';
                    if (checkWin('X')) {
                        board[row][col] = '-';
                        return new int[]{row, col};
                    }
                    board[row][col] = '-';
                }
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (table.isAvailable(row, col)) {
                    board[row][col] = 'O';
                    if (checkWin('O')) {
                        board[row][col] = '-';
                        return new int[]{row, col};
                    }
                    board[row][col] = '-';
                }
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (table.isAvailable(row, col)) {
                    board[row][col] = 'X';
                    int moveValue = minimax(table, 12, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
                    board[row][col] = '-';

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
        Menu menu=new Menu();
        int[] move = getBestMove(table);
        if (move[0] != -1 && move[1] != -1) {
            board[move[0]][move[1]] = 'X';
            System.out.println("AI MOVE: "+move[0]+","+move[1]);

            availableMoves--;
        }
        if(checkWin('X')){
            System.out.println("X WINS!");
            table.printBoard();
            menu.endGame();
        }

        if(availableMoves<=0){
            System.out.println("TIE!");
            table.printBoard();
            menu.endGame();
        }


    }

    public static int getScore() {
        // Immediate win/loss checks
        if (checkWin('X')) return 1_000_000;
        if (checkWin('O')) return -1_000_000;

        int score = 0;
        boolean hasPotentialWin = false;

        // Evaluate all positions
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Evaluate X opportunities
                if (board[row][col] == 'X') {
                    for (int[] dir : directions) {

                        LineInfo info = evaluateLine(row, col, dir, 'X');
                        if (info.openEnds > 0) { // Only count lines with potential
                            if (info.count >= 4) {
                                score += 100_000;
                                hasPotentialWin = true;
                            }

                            else if (info.count == 3) score += (info.openEnds == 2) ? 15_000 : 10_000;
                            else if (info.count == 2) score += (info.openEnds == 2) ? 3_000 : 1_000;
                        }
                    }
                }
                // Evaluate O threats
                else if (board[row][col] == 'O') {
                    for (int[] dir : directions) {
                        LineInfo info = evaluateLine(row, col, dir, 'O');
                        if (info.openEnds > 0) {
                            if (info.count >= 4) score -= 300_000;
                            else if (info.count == 3) score -= (info.openEnds == 2) ? 50_000 : 30_000;
                            else if (info.count == 2) score -= (info.openEnds == 2) ? 5_000 : 2_000;
                        }
                    }
                }
            }
        }

        // Bonus for center control if no immediate threats
        if (!hasPotentialWin && board[SIZE/2][SIZE/2] == 'X') {
            score += 1_000;
        }

        return score;
    }

    private static class LineInfo {
        int count;
        int openEnds;
    }

    private static LineInfo evaluateLine(int row, int col, int[] dir, char player) {
        LineInfo info = new LineInfo();
        info.count = 1; // Current position

        // Check in positive direction
        info.openEnds += checkDirection(row, col, dir[0], dir[1], player, info);
        // Check in negative direction
        info.openEnds += checkDirection(row, col, -dir[0], -dir[1], player, info);

        return info;
    }

    private static int checkDirection(int row, int col, int rowDir, int colDir, char player, LineInfo info) {
        int r = row + rowDir;
        int c = col + colDir;
        int openEnd = 0;

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
            if (board[r][c] == player) {
                info.count++;
            } else if (board[r][c] == '-') {
                openEnd = 1;
                break;
            } else {
                break; // Blocked by opponent
            }
            r += rowDir;
            c += colDir;
        }
        return openEnd;
    }


}

