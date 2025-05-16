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

    public static int[] getBestMove(Table table) {
       //megnézi hogy egyből nyerhet-e
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

        // blokkol  ha a játékosnak 4 érintkezik
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


        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (table.isAvailable(row, col)) {
                    board[row][col] = 'X';
                    int moveValue = minimax(table, 3, Integer.MIN_VALUE,
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
           table.availableMoves--;
        }
        if(checkWin('X')){
            System.out.println("X WINS!");
            table.printBoard();
        menu.endGame();
        }


    }




    public static int getScore() {

        if (checkWin('X')) return 1000000;  // AI nyer
        if (checkWin('O')) return -1000000; // Player nyer

        int score = 0;


        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                if (board[row][col] == 'X') {
                    for (int[] dir : directions) {
                        int count = countConsecutive(row, col, dir[0], dir[1], 'X');
                        if (count >= 4) score += 100000;  // nyerés
                        else if (count == 3) score += 10000; // fenyegetés
                        else if (count == 2) score += 100;    // lehetőség
                    }
                }
                // Evaluate O threats (heavier penalties)
                else if (board[row][col] == 'O') {
                    for (int[] dir : directions) {
                        int count = countConsecutive(row, col, dir[0], dir[1], 'O');
                        if (count >= 4) score -= 300000;  // muszáj blokkolni
                        else if (count == 3)score -= 100000; // kritikus
                        else if (count == 2) score -= 1000;   // veszély esélye
                    }
                }
            }
        }
        return score;
    }




}

