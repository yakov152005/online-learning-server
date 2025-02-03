package org.server.onlinelearningserver.utils;

import java.util.Random;

public class RandomUtils {
    public static final Random random = new Random();

    public static int generateRandomNumber(int difficulty) {
        return (random.nextInt(1, 10)) * difficulty;
    }

    public static int generateRandomNumberForN(int difficulty) {
        return (random.nextInt(1, 10)) + difficulty;
    }

    public static int generateRandomForQuadEquNumber(int difficulty) {
        if (difficulty == 1){
            difficulty = generateRandomNumber(difficulty);
        }
        return random.nextInt(difficulty * 2 + 1) - difficulty;
    }

    public static int generateRandomNumberPow(int difficulty) {
        return (int) (Math.random() * Math.pow(10, difficulty)) + 1;
    }

    public static String calculateResult(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> String.valueOf(a + b);
            case "-" -> String.valueOf(a - b);
            case "*" -> String.valueOf(a * b);
            case "/" -> String.valueOf(a / b);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
