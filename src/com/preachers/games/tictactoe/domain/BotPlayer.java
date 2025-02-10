package com.preachers.games.tictactoe.domain;
import java.util.Random;
import javax.swing.JButton;

public class BotPlayer {
    private final GameLogic gameLogic;
    private final Random random = new Random();

    public BotPlayer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void botMove() {
        JButton[][] board = gameLogic.getBoard();
        if (board == null) return;

        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board[row][col].getText().isEmpty());

        gameLogic.botMove(row, col);
    }
}