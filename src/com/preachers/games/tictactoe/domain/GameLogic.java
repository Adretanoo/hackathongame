package com.preachers.games.tictactoe.domain;

import com.preachers.games.tictactoe.ui.GameBoard;
import java.awt.Color;
import javax.swing.*;

public class GameLogic {
    private final GameBoard gameBoard;
    private final JButton[][] board;
    private String currentPlayer;
    private boolean gameOver;
    private int turns;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.board = gameBoard.getBoard();
        this.currentPlayer = "X";
        this.gameOver = false;
        this.turns = 0;
    }

    public JButton[][] getBoard() {
        return board;
    }

    public void playerMove(int row, int col) {
        if (gameOver || !board[row][col].getText().isEmpty()) return;

        board[row][col].setText(currentPlayer);
        turns++;
        checkWinner();

        if (!gameOver) {
            switchPlayer();
            gameBoard.getBot().botMove();
        }
    }

    public void botMove(int row, int col) {
        if (!gameOver && board[row][col].getText().isEmpty()) {
            board[row][col].setText("O");
            turns++;
            checkWinner();
            if (!gameOver) switchPlayer();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
        gameBoard.getTextLabel().setText(currentPlayer.equals("X") ? "Ваш хід!" : "Бот ходить...");
    }

    private void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        if (turns == 9) {
            gameBoard.getTextLabel().setText("Нічия!");
            gameOver = true;
        }
    }

    private void setWinner(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.GREEN);
        }
        gameBoard.getTextLabel().setText(currentPlayer + " переміг!");
        gameOver = true;
    }
}