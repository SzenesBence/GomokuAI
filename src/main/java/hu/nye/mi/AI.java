package hu.nye.mi;
import static hu.nye.mi.Table.board;
import static hu.nye.mi.Table.checkWin;

public class AI {
    Table table = new Table();
    public int aiScore=1;
    public int playerScore=-1;
    public int tie=0;
    public void setX() {


        int bestRow =-1;
        int bestCol =-1;

        // This will be replaced by minimax score later
        for (int i = 0; i < Table.SIZE; i++) {
            for (int j = 0; j < Table.SIZE; j++) {
                if (table.isAvailable(i, j)) {
                    // Pick first available cell for now (replace with Minimax later)
                    bestRow = i;
                    bestCol = j;
                    break; // Remove this break if you want smarter scanning
                }
            }
            if (bestRow != -1) break;
        }

        // Place X on the board if a valid move was found(safety check)
        if (bestRow != -1 && bestCol != -1) {
            board[bestRow][bestCol] = 'X';
            System.out.println("AI placed X at: " + bestRow + "," + bestCol);
            if (checkWin(bestRow, bestCol)) {
                System.out.println("Player X wins!");
            }
        } else {
            System.out.println("AI: No valid moves left.");
        }
    }

    private int minimax(char[][] board,int depth,boolean isMaximizing) {
        return 0;
    }


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
    /*
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

     */


}
