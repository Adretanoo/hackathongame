package com.preachers.hashing.ui;

import com.preachers.games.snake.ui.GameFrame;
import com.preachers.games.tictactoe.domain.TicTacToeGame;
import com.preachers.hashing.domain.BlockProcessor;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;

public class MainMenu extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JCheckBox logCheckBox;
    public static boolean loggingEnabled = false;

    public MainMenu() {
        setTitle("Hashing Algorithm");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Введіть повідомлення:");
        inputField = new JTextField(20);

        JButton hashButton = new JButton("Обчислити хеш");
        JButton clearButton = new JButton("Очистити поля"); // Додали кнопку очистки
        outputArea = new JTextArea(8, 30);
        outputArea.setEditable(false);

        logCheckBox = new JCheckBox("Увімкнути логування");
        logCheckBox.addActionListener(e -> loggingEnabled = logCheckBox.isSelected());

        hashButton.addActionListener(this::startGame);
        clearButton.addActionListener(e -> clearFields()); // Очистка при натисканні

        add(inputLabel);
        add(inputField);
        add(hashButton);
        add(clearButton); // Додаємо кнопку до форми
        add(logCheckBox);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }


    private void startGame(ActionEvent e) {
        String inputText = inputField.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Поле введення не може бути порожнім!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Random random = new Random();
        int choice = random.nextInt(2); // 0 або 1

        if (choice == 0) {
            new TicTacToeGame(this);
        } else {
            new GameFrame();
        }
    }

    public void processHash() {
        String inputText = inputField.getText();
        byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);
        int messageLength = inputBytes.length * 8;

        byte[][] blocks = BlockProcessor.createBlocks(inputBytes, messageLength);
        byte[][] processedBlocks = BlockProcessor.applyRounds(blocks, 10, messageLength);
        byte[] finalHash = BlockProcessor.finalizeBlocks(processedBlocks);

        StringBuilder hexHash = new StringBuilder();
        for (byte b : finalHash) {
            hexHash.append(String.format("%02X", b));
        }

        outputArea.setText("Результат хешування:\n" + hexHash);
    }

    private void clearFields() {
        inputField.setText("");
        outputArea.setText("");
    }
}
