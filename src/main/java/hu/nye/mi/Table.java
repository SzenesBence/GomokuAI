package hu.nye.mi;


public class Table {
    public static int SIZE = 15;
    public static char[][] board = new char[SIZE][SIZE];
    public static int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

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
            System.out.println("Space is taken!");
            return false;
        }

        return true;
    }
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < Table.SIZE && col >= 0 && col < Table.SIZE;
    }

  public void setO(int row,int col) {
        if(isAvailable(row,col)&&isInBounds(row,col)) {
            board[row][col] = 'O';
            if (checkWin(row, col)) {
                System.out.println("Player O wins!");

            }
        }

  }

    public static boolean checkWin(int row, int col) {
        char current = board[row][col];
        if (current == '-') return false;


        for (int[] dir : directions) {
            int count = 1;


            count += countConsecutive(row, col, dir[0], dir[1], current);

            count += countConsecutive(row, col, -dir[0], -dir[1], current);

            if (count >= 5) {
                return true;
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

