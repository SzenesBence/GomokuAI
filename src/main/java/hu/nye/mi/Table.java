package hu.nye.mi;



public class Table {

    public static int SIZE = 15;
    public static char[][] board = new char[SIZE][SIZE];
    public static int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
    public static int availableMoves = SIZE * SIZE;
    public void createBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-';
            }
        }
    }
    public void printBoard() {

        System.out.print("   ");
        for (int j = 0; j < SIZE; j++) {
            System.out.print(j + " ");
        }
        System.out.println();


        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + (i < 10 ? "  " : " "));
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean isAvailable(int row, int col) {
        return board[row][col] == '-';
    }
    public boolean anyMovesAvailable() {
        return availableMoves <= 0;
    }


    public void setO(int row, int col) {
        Menu menu=new Menu();
            board[row][col]='O';
        availableMoves--;
            if(checkWin('O')){
                System.out.println("O WINS!");
                printBoard();
                menu.endGame();
                System.exit(0);
            }
        if(availableMoves<=0){
            System.out.println("TIE!");
          printBoard();
            menu.endGame();
        }


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

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE &&
                board[r][c] == player) {
            count++;
            r += rowDir;
            c += colDir;
        }
        return count;
    }

  }



