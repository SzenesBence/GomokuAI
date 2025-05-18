package hu.nye.mi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static hu.nye.mi.Table.SIZE;
import static hu.nye.mi.Table.board;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public void displayMenu() {

        Player player= new Player();
    while (true) {
        System.out.println("1.Play");
        System.out.println("2.Load and Play");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Selected: Play");

                Table table = new Table();
                table.createBoard();
                table.printBoard();
                player.playerMenu();
                

            }
            case 2 -> {
                System.out.println("Load and play");
                loadGame("game1");
               player.playerMenu();

            }
            case 3 -> {
                System.out.println("Exiting the program. Goodbye,");
               endGame();

            }

            default -> System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }
}
public void endGame(){

        scanner.close();
    System.exit(0);
}

    public void loadGame(String filename) {
        String filePath = "C:\\JAVA GYAKORLATOK\\Gomoku\\src\\main\\resources\\" + filename + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {


            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < SIZE) {
                for (int col = 0; col < line.length() && col < SIZE; col++) {
                    board[row][col] = line.charAt(col);
                }
                row++;
            }
            System.out.println("Game loaded from " + filePath);
        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
        }
    }

}
