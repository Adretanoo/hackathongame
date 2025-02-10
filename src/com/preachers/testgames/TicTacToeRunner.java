package com.preachers.testgames;

import com.preachers.games.tictactoe.ui.GameBoard;
import javax.swing.SwingUtilities;

public class TicTacToeRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }

}
