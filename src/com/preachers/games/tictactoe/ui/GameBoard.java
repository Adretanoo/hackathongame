package com.preachers.games.tictactoe.ui;
import com.preachers.games.tictactoe.domain.BotPlayer;
import com.preachers.games.tictactoe.domain.GameLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard {
    private final int boardSize = 3;
    private JFrame frame;
    private JLabel textLabel;
    private JButton[][] board;
    private GameLogic gameLogic;
    private BotPlayer bot;

    public GameBoard() {
        frame = new JFrame("Tic-Tac-Toe");
        textLabel = new JLabel("Ваш хід!", SwingConstants.CENTER);
        board = new JButton[boardSize][boardSize];
        gameLogic = new GameLogic(this);
        bot = new BotPlayer(gameLogic);

        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.WHITE);
        frame.add(textLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFocusable(false);

                int row = r, col = c;
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        gameLogic.playerMove(row, col);
                    }
                });
            }
        }

        frame.setVisible(true);
    }

    public JButton[][] getBoard() {
        return board;
    }

    public JLabel getTextLabel() {
        return textLabel;
    }

    public BotPlayer getBot() {
        return bot;
    }
}

