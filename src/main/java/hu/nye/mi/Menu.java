package hu.nye.mi;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public void displayMenu() {


    while (true) {
        System.out.println("1.Play");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Selected: Play");
                Player player= new Player();
                Table table = new Table();
                table.createBoard();
                table.printBoard();
                player.playerMenu();
                

            }
            case 2 -> {
                System.out.println("Exiting the program. Goodbye,");
               closeScanner();

            }

            default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
        }
    }
}
public void closeScanner(){
        scanner.close();
    System.exit(0);
}
}
