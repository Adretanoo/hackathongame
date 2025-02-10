package com.preachers.hashing.domain;

import com.preachers.hashing.ui.MainMenu;
import com.preachers.hashing.utils.Euclid;
import com.preachers.hashing.utils.Utils;
import java.math.BigInteger;

public class BlockProcessor {

    public static byte[][] createBlocks(byte[] inputArray, int messageLength) {
        int blockSize = 32; // 256 бітів = 32 байти
        int totalBlocks = (inputArray.length + blockSize - 1) / blockSize;
        if ((inputArray.length % blockSize) + 4 > blockSize) {
            totalBlocks++; // Додатковий блок, якщо місце для довжини не вміщується
        }

        if (MainMenu.loggingEnabled) {
            System.out.println("🔹 Кількість блоків: " + totalBlocks);
        }

        byte[][] blocks = new byte[totalBlocks][blockSize];

        for (int blockIndex = 0; blockIndex < totalBlocks; blockIndex++) {
            byte[] block = new byte[blockSize];

            // Копіюємо дані у поточний блок
            int start = blockIndex * blockSize;
            int length = Math.min(inputArray.length - start, blockSize);
            System.arraycopy(inputArray, start, block, 0, length);

            if (MainMenu.loggingEnabled) {
                System.out.println("🔹 Початковий блок A:");
                Utils.printBlock(block);
            }

            // Зберігаємо блок
            blocks[blockIndex] = block;
        }

        return blocks;
    }

    public static byte[][] applyRounds(byte[][] blocks, int rounds, int messageBitLength) {
        int blockSize = blocks[0].length;

        for (int round = 0; round < rounds; round++) {
            if (MainMenu.loggingEnabled) {
                System.out.println("=== 🔄 Раунд обробки: " + (round + 1) + " ===");
            }

            for (int blockIndex = 0; blockIndex < blocks.length; blockIndex++) {
                byte[] block = blocks[blockIndex];

                // Генерація розширеної константи
                byte[] extendedConstant = Utils.generateExtendedConstant((char) (round % 10 + '0'), blockSize, messageBitLength);
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D Розширена константа B для блоку " + (blockIndex + 1) + ":");
                    Utils.printBlock(extendedConstant);
                }

                // Формула: A XOR B
                for (int i = 0; i < blockSize; i++) {
                    block[i] ^= extendedConstant[i];
                }
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D Після XOR: A XOR B для блоку " + (blockIndex + 1) + ":");
                    Utils.printBlock(block);
                }

                // Побітове зміщення (фіксоване)
                for (int i = 0; i < blockSize; i++) {
                    block[i] = (byte) ((block[i] << 3) | ((block[i] & 0xFF) >>> (8 - 3)));
                }
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D Після зміщення вліво для блоку " + (blockIndex + 1) + ":");
                    Utils.printBlock(block);
                }

                // Обчислення НСК між блоком та константою
                BigInteger blockValue = new BigInteger(1, block);
                BigInteger constantValue = new BigInteger(1, extendedConstant);
                BigInteger lcmValue = Euclid.lcm(blockValue, constantValue);
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D НСК(A, B) для блоку " + (blockIndex + 1) + ": " + lcmValue.toString(2));
                }

                // Формула: (A XOR B) XOR НСК(A, B)
                byte[] lcmBytes = lcmValue.toByteArray();
                for (int i = 0; i < blockSize; i++) {
                    block[i] ^= lcmBytes[i % lcmBytes.length];
                }
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D Після XOR із НСК для блоку " + (blockIndex + 1) + ":");
                    Utils.printBlock(block);
                }

                // Побітове зміщення (залежить від раунду)
                int shiftVariable = (round % 8) + 1;
                for (int i = 0; i < blockSize; i++) {
                    block[i] = (byte) ((block[i] >>> shiftVariable) | (block[i] << (8 - shiftVariable)));
                }
                if (MainMenu.loggingEnabled) {
                    System.out.println("\uD83D\uDD0D Після зміщення вправо для блоку " + (blockIndex + 1) + ":");
                    Utils.printBlock(block);
                }

                // Оновлення блоку після раунду
                blocks[blockIndex] = block;
            }
        }

        return blocks;
    }

    public static byte[] finalizeBlocks(byte[][] blocks) {
        int blockSize = blocks[0].length;
        byte[] result = new byte[blockSize];

        // Ініціалізуємо результат початковими значеннями
        for (int i = 0; i < blockSize; i++) {
            result[i] = 0;
        }

        // Об'єднуємо всі блоки за допомогою XOR
        for (byte[] block : blocks) {
            for (int i = 0; i < blockSize; i++) {
                result[i] ^= block[i];
            }
        }

        // Додаткове побітове зміщення для фіналізації
        int shiftFinal = 5; // Фіксоване зміщення для фіналізації
        for (int i = 0; i < blockSize; i++) {
            result[i] = (byte) ((result[i] << shiftFinal) | ((result[i] & 0xFF) >>> (8 - shiftFinal)));
        }

        return result;
    }
}

