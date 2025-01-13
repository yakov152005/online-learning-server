package org.server.onlinelearningserver.services;
import org.server.onlinelearningserver.entitys.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionGenerator {
    private static final Random random = new Random();
    private static final List<String> hebrewBoysNames = List.of("אורי", "יובל", "איתי", "נועם", "תומר");
    private static final List<String> hebrewGirlsNames = List.of("עדי", "נועה", "ליאן", "שילת", "קארין");
    public static final String ADD = "addition";
    public static final String SUB = "subtraction";
    public static final String MULTI = "multiplication";
    public static final String DIV = "division";
    public static final char[] OPERATORS = {'+','-','*','/' };
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    public static final int LEVEL_THREE = 3;
    public static final int LEVEL_FOUR = 4;
    //private static final String fractionQ1 = "הלך לקנות תפוזים "

    public Question generateQuestion(String category, int difficulty) {

        return switch (category) {
            case ADD -> generateAdditionQuestion(difficulty);
            case SUB -> generateSubtractionQuestion(difficulty);
            case MULTI -> generateMultiplicationQuestion(difficulty);
            case DIV -> generateDivisionQuestion(difficulty);
            //case "wordProblem" -> generateWordProblem(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }


    private Question generateAdditionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;

        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                content = a + " + " + b + " = ?";
                solution = calculateResult(a,b,"+");
                explanation = "To solve this, simply add " + a + " and " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
                int result = a + generateRandomNumber(difficulty);
                content = a + " + " + "x" + " = " + result;
                solution = calculateResult(result,a,"-");
                explanation = "To find x, subtract " + a + " from " + result + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
        return new Question("addition", content, difficulty, solution,explanation);
    }

    private Question generateSubtractionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                content = a + " - " + b + " = ?";
                solution = calculateResult(a,b,"-");
                explanation = "To solve this, simply subtract " + a + "less" + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
                int result =  generateRandomNumber(difficulty);
                b = a - result;
                content = a + " - " + "x" + " = " + b;
                solution = String.valueOf(result);
                explanation = "To find x, subtract " + result + " from " + a + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
        return new Question("subtraction", content, difficulty, solution,explanation);
    }

    private Question generateMultiplicationQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
                content = a + " * " + b + " = ?";
                solution = String.valueOf(a * b);
                explanation = "To solve this, simply multiply " + a + " and " + b + ".";
                break;
            case LEVEL_TWO:
                int result = a * generateRandomNumber(difficulty);
                b = result / a;
                content = a + " * " + "x" + " = " + result;
                solution = String.valueOf(b);
                explanation = "To find x, divide " + result + " by " + a + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }

        return new Question("multiplication", content, difficulty, solution,explanation);
    }



    private static Question generateDivisionQuestion(int difficulty) {
        int a = 0, b = 0, result = 0;
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                if (difficulty == LEVEL_ONE){
                    a = (random.nextInt(1,10) ) * difficulty;
                    b = a * (random.nextInt(1,10) ) * difficulty;
                }
                if (difficulty == LEVEL_TWO){
                    a = (random.nextInt(1,10) ) * difficulty;
                    b = a * ((int) (Math.random() * Math.pow(10, difficulty)));
                }

                content = b + " / " + a + " = ?";
                solution = String.valueOf(b / a);
                explanation = "To solve this, simply division " + a + " by " + b + ".";

                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
                if (difficulty == LEVEL_THREE){
                    result = (random.nextInt(1,10) ) * difficulty;
                }
                if (difficulty == LEVEL_FOUR){
                    result = (int) (Math.random() * Math.pow(3, difficulty)) + 1;
                }
                b =  (random.nextInt(1,10) ) * difficulty;

                a = b * result;
                content = "x / " + b + " = " + result;
                solution = String.valueOf(a);
                explanation = "To find x, multiply " + result + " by " + a + ".";

                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }

        return new Question("division", content, difficulty, solution,explanation);
    }


    private int generateRandomNumber(int difficulty) {
        return (int) (Math.random() * Math.pow(10, difficulty)) + 1;
    }

    private String calculateResult(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> String.valueOf(a + b);
            case "-" -> String.valueOf(a - b);
            case "*" -> String.valueOf(a * b);
            case "/" -> String.valueOf(a / b);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    private String getOperatorDescription(String operator) {
        return switch (operator) {
            case "+" -> "add";
            case "-" -> "subtract";
            case "*" -> "multiply";
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    /*


import java.util.List;
import java.util.Random;

@Service
public class QuestionGenerator {
    private static final Random random = new Random();
    private static final List<String> hebrewBoysNames = List.of("אורי", "יובל", "איתי", "נועם", "תומר");
    private static final List<String> hebrewGirlsNames = List.of("עדי", "נועה", "ליאן", "שילת", "קארין");

    //private static final String fractionQ1 = "הלך לקנות תפוזים "

    public Question generateQuestion(String category, int difficulty) {

        return switch (category) {
            case "addition" -> generateAdditionQuestion(difficulty);
            case "subtraction" -> generateSubtractionQuestion(difficulty);
            case "multiplication" -> generateMultiplicationQuestion(difficulty);
            case "division" -> generateDivisionQuestion(difficulty);
            //case "wordProblem" -> generateWordProblem(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }

    private Question generateAdditionQuestion(int difficulty) {
        int a, b;
        String content, solution, explanation;
        switch (difficulty) {
            case 1:
            case 2:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                content = a + " + " + b + " = ?";
                solution = String.valueOf(a + b);
                explanation = "To solve this, simply add " + a + " and " + b + ".";
                return new Question("addition", content, difficulty, solution,explanation);
            case 3:
            case 4:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                int result = a + (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = result - a;
                content = a + " + " + "x" + " = " + result;
                solution = String.valueOf(b);
                explanation = "To find x, subtract " + a + " from " + result + ".";
                return new Question("addition", content, difficulty, solution,explanation);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }

    }

    private Question generateSubtractionQuestion(int difficulty) {
        int a, b;
        String content, solution, explanation;
        switch (difficulty) {
            case 1:
            case 2:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                content = a + " - " + b + " = ?";
                solution = String.valueOf(a - b);
                explanation = "To solve this, simply subtract " + a + "less" + b + ".";
                return new Question("subtraction", content, difficulty, solution,explanation);
            case 3:
            case 4:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                int result = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = a - result;
                content = a + " - " + "x" + " = " + b;
                solution = String.valueOf(result);
                explanation = "To find x, addition " + a + " and " + result + ".";
                return new Question("subtraction", content, difficulty, solution,explanation);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
    }

    private Question generateMultiplicationQuestion(int difficulty) {
        int a, b;
        String content, solution, explanation;
        switch (difficulty) {
            case 1:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                content = a + " * " + b + " = ?";
                solution = String.valueOf(a * b);
                explanation = "To solve this, simply multiply " + a + " and " + b + ".";
                return new Question("multiplication", content, difficulty, solution,explanation);
            case 2:
                a = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                int result = a * (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                b = result / a;
                content = a + " * " + "x" + " = " + result;
                solution = String.valueOf(b);
                explanation = "To find x, divide " + result + " by " + a + ".";
                return new Question("multiplication", content, difficulty, solution,explanation);
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
    }



    private static Question generateDivisionQuestion(int difficulty) {
        int a = 0, b = 0, result = 0;
        String content, solution, explanation;
        switch (difficulty) {
            case 1:
            case 2:
                if (difficulty == 1){
                    a = (random.nextInt(1,10) ) * difficulty;
                    b = a * (random.nextInt(1,10) ) * difficulty;
                }
                if (difficulty == 2){
                    a = (random.nextInt(1,10) ) * difficulty;
                    b = a * ((int) (Math.random() * Math.pow(10, difficulty)));
                }

                content = b + " / " + a + " = ?";
                solution = String.valueOf(b / a);
                explanation = "To solve this, simply division " + a + " by " + b + ".";

                return new Question("division", content, difficulty, solution,explanation);
            case 3:
            case 4:
                if (difficulty == 3){
                    result = (random.nextInt(1,10) ) * difficulty;
                }
                if (difficulty == 4){
                    result = (int) (Math.random() * Math.pow(3, difficulty)) + 1;
                }
                b =  (random.nextInt(1,10) ) * difficulty;

                a = b * result;
                content = "x / " + b + " = " + result;
                solution = String.valueOf(a);
                explanation = "To find x, multiply " + result + " by " + a + ".";

                return new Question("division", content, difficulty, solution,explanation);
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }


    private Question generateWordProblem(int difficulty){

    }



    public static void main(String[] args) {
        Question question = generateDivisionQuestion(4);
        System.out.println(question);
        String randomName = hebrewGirlsNames.get(random.nextInt(hebrewGirlsNames.size()));

        System.out.println(randomName);
    }

    */


}