package hu.nye.mi;

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
                    System.out.println("Pick a place to set 'O'  by row and column");
                    System.out.print("Enter row (0-14): ");
                    row = scanner.nextInt();
                    System.out.print("Enter column (0-14): ");
                    col = scanner.nextInt();
                  table.setO(row,col);
                  table.checkWin(row,col);
            table.printBoard();

                    break;

                case 2:
                    System.out.println("Game Save and Exit");
                    break;


                default:
                    System.out.println("Invalid action. Please enter a number between 1 and 6.");
                    break;
            }
        }
    }

}
