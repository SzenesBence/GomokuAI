package hu.nye.mi;

public class Table {
    private final int SIZE = 15;
    private final char[][] board = new char[SIZE][SIZE];

    public void createBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE; j++) {
    board[i][j]='-';
            }
        }
    }
    public  void printBoard() {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }
}
