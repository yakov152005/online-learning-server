package org.server.onlinelearningserver.services;
import org.server.onlinelearningserver.entitys.Question;
import org.springframework.stereotype.Service;


import java.util.Random;

import static org.server.onlinelearningserver.utils.Constants.Question.*;

@Service
public class QuestionGenerator {

    private static final Random random = new Random();


    public Question generateQuestion(String category, int difficulty) {

        return switch (category) {
            case ADD -> generateAdditionQuestion(difficulty);
            case SUB -> generateSubtractionQuestion(difficulty);
            case MULTI -> generateMultiplicationQuestion(difficulty);
            case DIV -> generateDivisionQuestion(difficulty);
            case WORD_PROBLEM -> generateWordProblem(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }


    private static Question generateAdditionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        int result = 0;
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
                if (difficulty == LEVEL_THREE){
                    result = a + generateRandomNumber(difficulty);
                }
                if (difficulty == LEVEL_FOUR){
                    a = generateRandomNumberPow(difficulty);
                    result = a + generateRandomNumber(difficulty);
                }

                content = a + " + " + "x" + " = " + result;
                solution = calculateResult(result,a,"-");
                explanation = "To find x, subtract " + a + " from " + result + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
        return new Question(ADD, content, difficulty, solution,explanation);
    }

    private static Question generateSubtractionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                content = a + " - " + b + " = ?";
                solution = calculateResult(a,b,"-");
                explanation = "To solve this, simply subtract " + a + " less " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
                a = generateRandomNumberPow(difficulty);
                int result =  generateRandomNumberPow(difficulty);
                b = a - result;
                content = a + " - " + "x" + " = " + b;
                solution = String.valueOf(result);
                explanation = "To find x, subtract " + a + " from " + b + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }
        return new Question(SUB, content, difficulty, solution,explanation);
    }

    private static Question generateMultiplicationQuestion(int difficulty) {
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
                int result = a * generateRandomNumberPow(difficulty);
                b = result / a;
                content = a + " * " + "x" + " = " + result;
                solution = String.valueOf(b);
                explanation = "To find x, divide " + result + " by " + a + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown  difficulty: " + difficulty);
        }

        return new Question(MULTI, content, difficulty, solution,explanation);
    }



    private static Question generateDivisionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        int result = 0;
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                if (difficulty == LEVEL_ONE){
                    b *= a;
                }
                if (difficulty == LEVEL_TWO){
                    b = a * ((int) (Math.random() * Math.pow(4, difficulty)));
                }

                content = b + " / " + a + " = ?";
                solution = String.valueOf(b / a);
                explanation = "To solve this, simply division " + a + " by " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
                if (difficulty == LEVEL_THREE){
                    result = generateRandomNumber(difficulty);
                }
                if (difficulty == LEVEL_FOUR){
                    result = (int) (Math.random() * Math.pow(3, difficulty)) + 1;
                }

                a = b * result;
                content = "x / " + b + " = " + result;
                solution = String.valueOf(a);
                explanation = "To find x, multiply " + result + " by " + b + ".";
                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }

        return new Question(DIV, content, difficulty, solution,explanation);
    }

    private static Question generateWordProblem(int difficulty){
        int a = generateRandomNumber(difficulty) + 1;
        int b = generateRandomNumber(difficulty) + 1;
        String boyName = hebrewBoysNames.get(random.nextInt(hebrewBoysNames.size()));
        int result = 0;
        String content, solution, explanation;

        switch (difficulty){
            case LEVEL_ONE:
                String item1 = items1Names.get(random.nextInt(items1Names.size()));
                String item2 = items2Names.get(random.nextInt(items2Names.size()));

                result = a + b;

                content = String.format("%s שם %d %s %s ולאחר מכן הוסיף עוד %d. כמה %s יש ל%s עכשיו?", boyName, a, item2, item1, b,item2,boyName);
                solution = String.valueOf(result);
                explanation = String.format("ת. %d ועוד %d הם %d.", a, b, result);
                break;
            case LEVEL_TWO:

                String girlName = hebrewGirlsNames.get(random.nextInt(hebrewGirlsNames.size()));
                String fruitName = fruitNames.get(random.nextInt(fruitNames.size()));
                a += b;
                result = a - b ;

                content = String.format("%s קנתה %d %s, אך נתנה %d ל%s. כמה %s נשאר לה?", girlName, a, fruitName, b,boyName,fruitName);
                solution = String.valueOf(result);
                explanation = String.format("ת. %d פחות %d הם %d", a, b, result);

                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }

        return new Question(WORD_PROBLEM,content,difficulty,solution,explanation);
    }


    private static int generateRandomNumberPow(int difficulty) {
        return (int) (Math.random() * Math.pow(10, difficulty)) + 1;
    }

    private static int generateRandomNumber(int difficulty){
        return (random.nextInt(1,10) ) * difficulty;
    }
    private static String calculateResult(int a, int b, String operator) {
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

    public static void main(String[] args) {
        int count = 5;
        boolean isValid = true;
        while (isValid){
            System.out.println(generateAdditionQuestion(4));
            count --;
            if (count == 0){
                isValid = false;
            }
        }
    }


}