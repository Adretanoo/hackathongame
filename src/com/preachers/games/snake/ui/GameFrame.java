package com.preachers.games.snake.ui;
import com.preachers.games.snake.domain.GamePanel;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

    private static final long serialVersionUID = 1L;

    public GameFrame() {
        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
