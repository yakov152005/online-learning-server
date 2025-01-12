package org.server.onlinelearningserver.services;
import org.server.onlinelearningserver.entitys.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionGenerator {

    public Question generateQuestion(String category, int difficulty) {

        return switch (category) {
            case "addition" -> generateAdditionQuestion(difficulty);
            case "subtraction" -> generateSubtractionQuestion(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            case "multiplication" -> generateMultiplicationQuestion(difficulty);
            case "division" -> generateDivisionQuestion(difficulty);
            // case "wordProblem" -> generateWordProblem(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }

    private Question generateAdditionQuestion(int difficulty) {
        int x, y;
        String content, solution;
        switch (difficulty) {
            case 1:
                x = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                y = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                content = x + " + " + y + " = ?";
                solution = String.valueOf(x + y);
                return new Question("addition", content, difficulty, solution);
            case 2:
                x = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                int result = x + (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                y = result - x;
                content = x + " + " + "?" + " = " + result;
                solution = String.valueOf(y);
                return new Question("addition", content, difficulty, solution);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }

    }

    private Question generateSubtractionQuestion(int difficulty) {
        int x, y;
        String content, solution;
        switch (difficulty) {
            case 1:
                x = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                y = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                content = x + " - " + y + " = ?";
                solution = String.valueOf(x - y);
                return new Question("subtraction", content, difficulty, solution);
            case 2:
                x = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                int result = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                y = x - result;
                content = x + " - " + "?" + " = " + y;
                solution = String.valueOf(result);
                return new Question("subtraction", content, difficulty, solution);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
    }

    private Question generateMultiplicationQuestion(int difficulty) {
        int x, y;
        String content, solution;
        switch (difficulty) {
            case 1:
                x = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                y = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                content = x + " * " + y + " = ?";
                solution = String.valueOf(x * y);
                return new Question("multiplication", content, difficulty, solution);
            case 2:
                x = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                int result = x * (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                y = result / x;
                content = x + " * " + "?" + " = " + result;
                solution = String.valueOf(y);
                return new Question("multiplication", content, difficulty, solution);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
    }

    private Question generateDivisionQuestion(int difficulty) {
        int x, y;
        String content, solution;
        switch (difficulty) {
            case 1:
                y = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                x = y * ((int) (Math.random() * Math.pow(5, difficulty))) + 1;
                content = x + " / " + y + " = ?";
                solution = String.valueOf(x / y);
                return new Question("division", content, difficulty, solution);
            case 2:
                y = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                int result = (int) (Math.random() * Math.pow(5, difficulty)) + 1;
                x = y * result;
                content = "? / " + y + " = " + result;
                solution = String.valueOf(x);
                return new Question("division", content, difficulty, solution);
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }


}