package hu.nye.mi;



import static hu.nye.mi.Table.board;
import static hu.nye.mi.Table.checkWin;

public class AI {
    public void setX() {
        Table table = new Table();

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
   public enum scores{
        X(1),
        O(-1),
       tie(0);
       int score;
       scores(int score) {
           this.score = score;
       }
   }


}
