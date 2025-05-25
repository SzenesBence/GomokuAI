package hu.nye.mi.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMenu {
    private JFrame mainMenuFrame;
    private int boardSize = 5;

    public SwingMenu() {
        createMainMenu();
    }

    private void createMainMenu() {
        mainMenuFrame = new JFrame("Gomoku");
        mainMenuFrame.setLayout(new GridLayout(3, 1, 10, 10));
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton newGame = new JButton("New Game");
        JButton loadPlay = new JButton("Load and Play");
        JButton exit = new JButton("Exit");

        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        newGame.setFont(buttonFont);
        loadPlay.setFont(buttonFont);
        exit.setFont(buttonFont);

        // Add action listeners
        newGame.addActionListener(e -> createGameBoard());
        exit.addActionListener(e -> System.exit(0));

        // Add components
        mainMenuFrame.add(newGame);
        mainMenuFrame.add(loadPlay);
        mainMenuFrame.add(exit);

        // Configure window
        mainMenuFrame.pack();
        mainMenuFrame.setSize(300, 300);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setVisible(true);

        // In createMainMenu():
        JPanel sizePanel = new JPanel();
        JLabel sizeLabel = new JLabel("Board Size:");
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(5, 3, 15, 1));
        sizePanel.add(sizeLabel);
        sizePanel.add(sizeSpinner);
        mainMenuFrame.add(sizePanel);

// Modify newGame action listener:
        newGame.addActionListener(e -> {
            boardSize = (Integer) sizeSpinner.getValue();
            createGameBoard();
        });
    }

    private void createGameBoard() {
        // Close main menu
        mainMenuFrame.dispose();

        // Create game board
        JFrame gameFrame = new JFrame("Gomoku - Game Board");
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons for each cell
        JButton[][] buttons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                int finalI = i, finalJ = j;
                buttons[i][j].addActionListener(e -> handleCellClick(finalI, finalJ, buttons));
                boardPanel.add(buttons[i][j]);
            }
        }



        JButton saveExit = new JButton("Save and Exit");
        saveExit.addActionListener(e -> {
            gameFrame.dispose();
            createMainMenu();
        });

        // Layout
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.add(saveExit, BorderLayout.SOUTH);

        // Configure window
        gameFrame.setSize(600, 600);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);

// In createGameBoard():
        int cellSize = Math.max(40, 600/boardSize); // Dynamic cell sizing
        gameFrame.setSize(cellSize * boardSize, cellSize * boardSize);
    }


    private void handleCellClick(int row, int col, JButton[][] buttons) {
        buttons[row][col].setText("O");

    }


}