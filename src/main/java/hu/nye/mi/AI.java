package hu.nye.mi;

import static hu.nye.mi.Table.*;

public class AI {
    Table table = new Table();


    public static int minimax(Table table, int depth,int alpha, int beta, boolean isMax) {

        int boardVal = getScore();

        if (checkWin('X')) return 10000;
        if (checkWin('O')) return -10000;
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

    public static int getScore() {
        if (checkWin('X')) return 1000000;
        if (checkWin('O')) return -1000000;

        int score = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != '-') {
                    char player = board[row][col];
                    boolean isAI = (player == 'X');

                    for (int[] dir : directions) {

                        if (canFormFive(row, col, dir)) {
                            int count = 1 +
                                    countConsecutive(row, col, dir[0], dir[1], player) +
                                    countConsecutive(row, col, -dir[0], -dir[1], player);

                            if (isAI) {
                                if (count >= 4) score += 100000;
                                else if (count == 3) score += 10000;
                            } else {
                                if (count >= 4) score -= 300000;
                                else if (count == 3) score -= 100000;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    public static boolean canFormFive(int row, int col, int[] dir) {
        int potential = 1;
        potential += countPotential(row, col, dir[0], dir[1]);
        potential += countPotential(row, col, -dir[0], -dir[1]);
        return potential >= 5;
    }

    private static int countPotential(int row, int col, int rowDir, int colDir) {
        int count = 0;
        int r = row + rowDir;
        int c = col + colDir;

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE &&
                (board[r][c] == '-' || board[r][c] == board[row][col])) {
            count++;
            r += rowDir;
            c += colDir;
        }
        return count;
    }
    public static int[] getBestMove(Table table) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (table.isAvailable(row, col)) {
                    board[row][col] = 'X';
                    int moveValue = minimax(table, 4, Integer.MIN_VALUE,
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








}

