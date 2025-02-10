package com.preachers.games.tictactoe.domain;

import com.preachers.hashing.ui.MainMenu;
import java.awt.*;
import javax.swing.*;

public class TicTacToeGame extends JFrame {
    private final JButton[][] board = new JButton[3][3];
    private String currentPlayer = "X";
    private boolean gameOver = false;
    private int turns = 0;
    private final MainMenu mainMenu;

    public TicTacToeGame(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton("");
                board[r][c] = tile;
                tile.setFont(new Font("Arial", Font.BOLD, 50));
                tile.setFocusPainted(false);
                tile.addActionListener(e -> playerMove(tile));
                add(tile);
            }
        }
        setVisible(true);
    }

    private void playerMove(JButton tile) {
        if (gameOver || !tile.getText().isEmpty()) return;

        tile.setText(currentPlayer);
        turns++;

        checkWinner();
        if (!gameOver) {
            switchPlayer();
            botMove();
        }
    }

    private void botMove() {
        if (gameOver) return;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().isEmpty()) {
                    board[r][c].setText("O");
                    turns++;
                    checkWinner();
                    switchPlayer();
                    return;
                }
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }

    private void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0].getText());
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c].getText());
                return;
            }
        }

        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0].getText());
            return;
        }

        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2].getText());
            return;
        }

        if (turns == 9) {
            JOptionPane.showMessageDialog(this, "Нічия!", "Гра завершена", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void setWinner(String winner) {
        gameOver = true;
        JOptionPane.showMessageDialog(this, winner + " переміг!", "Гра завершена", JOptionPane.INFORMATION_MESSAGE);
        if (winner.equals("X")) {
            mainMenu.processHash();
        }
        dispose();
    }
}

