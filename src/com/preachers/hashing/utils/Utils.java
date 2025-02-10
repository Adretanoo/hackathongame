package com.preachers.hashing.utils;

public class Utils {
    private static boolean loggingEnabled = false;

    public static void printFinal16BitValue(byte[] result) {
        for (int i = 0; i < result.length; i += 2) {
            int combined = ((result[i] & 0xFF) << 8) | (result[i + 1] & 0xFF);
            System.out.printf("%04X ", combined);
        }
        System.out.println();
    }

    public static byte[] generateExtendedConstant(char lastChar, int blockSize, int messageBitLength) {
        int[] constMix = {
            0x6C30C9F2, 0xB6D5A493, 0xCC542D4E, 0x74B5C5A6,
            0x4D019F4D, 0x6631046D, 0x7728001A, 0x72EA8CA5,
            0xC3C8A5AC, 0x6D26DA97, 0xA34D2EF1, 0xC5BBE2B4,
            0x2A7FA051, 0xE94A3B15, 0x5A7B8D2F, 0x82F63B4C
        };
        byte[] extendedConstant = new byte[blockSize];

        // Розрахунок індексу з врахуванням довжини повідомлення
        int roundNumber = Character.getNumericValue(lastChar); // Номер раунду
        int index = (roundNumber + messageBitLength) % constMix.length;

        int constIndex = 0;
        for (int i = 0; i < blockSize; i += 4) {
            if (constIndex >= constMix.length) {
                constIndex = 0;
            }
            String hexValue = String.format("%08X", constMix[(index + constIndex) % constMix.length]);
            byte[] chunk = hexStringToByteArray(hexValue);
            System.arraycopy(chunk, 0, extendedConstant, i, Math.min(4, blockSize - i));
            constIndex++;
        }
        return extendedConstant;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void printBlock(byte[] block) {
        if (!loggingEnabled) return;
        for (byte b : block) {
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " ");
        }
        System.out.println();
    }
}
