package hu.nye.mi;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static hu.nye.mi.Table.*;

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
                case 1 -> {
                    Table table = new Table();
                    AI ai = new AI();
                    System.out.println("Pick a place to set 'O'  by row and column");
                    System.out.print("Enter row (0-4): ");
                    row = scanner.nextInt();
                    System.out.print("Enter column (0-4): ");
                    col = scanner.nextInt();
                    if (table.isAvailable(row, col)) {
                        table.setO(row, col);

                    } else {
                        System.out.println("Space is taken!");
                        break;
                    }

                    ai.setX();
                    checkWin('O');
                    checkWin('X');
                    table.printBoard();

                }
                case 2 -> {
                    System.out.println("Game Save and Exit");
                    save("game1");
                    Menu menu = new Menu();
                    menu.displayMenu();
                    System.out.println("Exiting the program. Goodbye,");
                    System.exit(0);
                }
                default -> System.out.println("Invalid action. Please enter a number between 1 and 2.");
            }
        }
    }

    public void save(String filename) {

        String filePath = "C:\\JAVA GYAKORLATOK\\Gomoku\\src\\main\\resources\\" + filename + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    writer.print(board[i][j]);
                }
                writer.println();
            }
            System.out.println("Game saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }
}
