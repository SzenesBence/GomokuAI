package hu.nye.mi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    public int row;
    public int col;



    public void playerMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Actions:");
            System.out.println("1. Place");
            System.out.println("2. Save and Exit");

            System.out.print("Enter your action: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    Table table = new Table();
                    AI ai=new AI();
                    System.out.println("Pick a place to set 'O'  by row and column");
                    System.out.print("Enter row (0-14): ");
                    row = scanner.nextInt();
                    System.out.print("Enter column (0-14): ");
                    col = scanner.nextInt();
                  table.setO(row,col);
                  table.checkWin(row,col);
               ai.setX();
                  //OPlaced(row,col);
                  //getoPlacements();

            table.printBoard();

                    break;

                case 2:
                    System.out.println("Game Save and Exit");
                    scanner.close();
                    System.out.println("Exiting the program. Goodbye,");
                    System.exit(0);
                    break;


                default:
                    System.out.println("Invalid action. Please enter a number between 1 and 6.");
                    break;
            }
        }
    }
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
/*
    private final static List<int[]> oPlacements = new ArrayList<>();

    public void OPlaced(int row, int col) {
        oPlacements.add(new int[]{row, col});
        printPlacements();
    }

    private void printPlacements() {
        for (int[] placement : oPlacements) {
            System.out.println("[" + placement[0] + ", " + placement[1] + "]");
        }
    }

    public List<int[]> getoPlacements() {
        return oPlacements;
    }

 */

}
