package hu.nye.mi;



public class Table {
    public static int SIZE = 5;
    public static char[][] board = new char[SIZE][SIZE];
    public static int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
    public int availableMoves = SIZE * SIZE;
    public void createBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE; j++) {
    board[i][j]='-';
            }
        }
    }
    public void printBoard() {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }
    public boolean isAvailable(int row, int col) {

        if (board[row][col] != '-') {
            return false;
        }

        return true;
    }
    public boolean anyMovesAvailable() {
        return availableMoves > 0;
    }
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < Table.SIZE && col >= 0 && col < Table.SIZE;
    }

    public void setO(int row, int col) {
board[row][col]='O';

            if(checkWin('O')){
                System.out.println("O WINS!");
                Menu menu=new Menu();
                menu.endGame();
                System.exit(0);
            }
            // If this is the FIRST 'O', make AI respond


    }

    public static boolean checkWin(char player) {

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == player) {
                    for (int[] dir : directions) {
                        int count = 1 +
                                countConsecutive(row, col, dir[0], dir[1], player) +
                                countConsecutive(row, col, -dir[0], -dir[1], player);
                        if (count >= 5) return true;
                    }
                }
            }
        }
        return false;
    }
    public static int countConsecutive(int row, int col, int rowDir, int colDir, char player) {
        int count = 0;
        int r = row + rowDir;
        int c = col + colDir;

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == player) {
            count++;
            r += rowDir;
            c += colDir;
        }
        return count;
    }


  }



