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
            case INVOICE_SERIES -> generateInvoiceSeries(difficulty);
            case QUADRATIC_EQUATION -> generateQuadraticEquation(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }

    private static Question generateQuadraticEquation(int difficulty){
        int x1 = generateRandomForQuadEquNumber(difficulty);
        int x2 = generateRandomForQuadEquNumber(difficulty);
        int a = generateRandomForQuadEquNumber(difficulty);
        int b = -a * (x1 + x2);
        int c = a * x1 * x2;
        String content, solution, explanation;
        switch (difficulty){
            case LEVEL_ONE:
            default:
                content = "A = " + a + ", B = " + b + ", C = " + c + ", Find x1,2 and write down the largest x among them.";
                solution = String.valueOf(Math.max(x1, x2));
                explanation = "To solve this, use the formula " +quadraticFormula;
        }
        return new Question(QUADRATIC_EQUATION,content,difficulty,solution,explanation);
    }

    private static Question generateInvoiceSeries(int difficulty) {
        int a1 = generateRandomNumber(difficulty);
        int d = generateRandomNumber(difficulty);
        int n = generateRandomNumberForN(difficulty);
        int an = a1 + ((n - 1) * d);
        int Sn = (n * (a1 + an)) / 2;
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
                content = "a1 = " + a1 + ", d = " + d + ", a" + n + " = ?";
                solution = String.valueOf(an);
                explanation = "To solve this, use the formula " + anFormula;
                break;
            case LEVEL_TWO:
                content = " an = " + an + ", d = " + d + ", n = " + n + " a1 = ?";
                solution = String.valueOf(a1);
                explanation = "To solve this, use the formula " + anFormula;
                break;
            case LEVEL_THREE:
                int n2 = generateRandomNumberForN(difficulty);
                int an2 = a1 + ((n2 - 1) * d);

                content = "a" + n + " = " + an + ", a" + n2 + " = " + an2 + ", a1 = ? " + ", d = ?" +
                          " *You should write the answer for a1 and not for d*";
                solution = String.valueOf(a1);
                explanation = "To solve this, [ a" + n + " = a1 + (" + n + " - 1) * d ]" + " - " +
                              "[ a" + n2 + " = a1 + (" + n2 + " - 1) * d ]";
                break;
            case LEVEL_FOUR:
                content = " a1 = " + a1 + ", d = " + d + ", n = " + n + ", S" + n + " = ?";
                solution = String.valueOf(Sn);
                explanation = "To solve this, use the formula " + snFormula;
                break;
            case LEVEL_FIVE:
            default:
                content = " a1 = " + a1 + ", d = " + d + ", Sn" + " = " + Sn + ", n = ?" +
                          " *Remember n cannot be a negative or decimal value.*";
                solution = String.valueOf(n);
                explanation = "Use the formulas " + snFormula + " And " + quadraticForInvoiceFormula;
                break;
        }
        return new Question(INVOICE_SERIES, content, difficulty, solution, explanation);
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
                solution = calculateResult(a, b, "+");
                explanation = "To solve this, simply add " + a + " and " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
            default:
                if (difficulty == LEVEL_THREE) {
                    result = a + generateRandomNumber(difficulty);
                }
                if (difficulty == LEVEL_FOUR) {
                    a = generateRandomNumberPow(difficulty);
                    result = a + generateRandomNumber(difficulty);
                }
                if (difficulty > LEVEL_FOUR){
                    a = generateRandomNumberPow(difficulty);
                    result = a + generateRandomNumber(difficulty);
                }

                content = a + " + " + "x" + " = " + result;
                solution = calculateResult(result, a, "-");
                explanation = "To find x, subtract " + a + " from " + result + ".";
                break;
        }
        return new Question(ADD, content, difficulty, solution, explanation);
    }

    private static Question generateSubtractionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                content = a + " - " + b + " = ?";
                solution = calculateResult(a, b, "-");
                explanation = "To solve this, simply subtract " + a + " less " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
            default:
                a = generateRandomNumberPow(difficulty);
                int result = generateRandomNumberPow(difficulty);
                b = a - result;
                content = a + " - " + "x" + " = " + b;
                solution = String.valueOf(result);
                explanation = "To find x, subtract " + a + " from " + b + ".";
                break;
        }
        return new Question(SUB, content, difficulty, solution, explanation);
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
            default:
                int result = a * generateRandomNumberPow(difficulty);
                b = result / a;
                content = a + " * " + "x" + " = " + result;
                solution = String.valueOf(b);
                explanation = "To find x, divide " + result + " by " + a + ".";
                break;
        }

        return new Question(MULTI, content, difficulty, solution, explanation);
    }


    private static Question generateDivisionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        int result = 0;
        String content, solution, explanation;
        switch (difficulty) {
            case LEVEL_ONE:
            case LEVEL_TWO:
                if (difficulty == LEVEL_ONE) {
                    b *= a;
                }
                if (difficulty == LEVEL_TWO) {
                    b = a * ((int) (Math.random() * Math.pow(4, difficulty)));
                }

                content = b + " / " + a + " = ?";
                solution = String.valueOf(b / a);
                explanation = "To solve this, simply division " + a + " by " + b + ".";
                break;
            case LEVEL_THREE:
            case LEVEL_FOUR:
            default:
                if (difficulty == LEVEL_THREE) {
                    result = generateRandomNumber(difficulty);
                }
                if (difficulty == LEVEL_FOUR) {
                    result = (int) (Math.random() * Math.pow(3, difficulty)) + 1;
                }
                if (difficulty > LEVEL_FOUR) {
                    result = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                }

                a = b * result;
                content = "x / " + b + " = " + result;
                solution = String.valueOf(a);
                explanation = "To find x, multiply " + result + " by " + b + ".";
                break;
        }

        return new Question(DIV, content, difficulty, solution, explanation);
    }

    private static Question generateWordProblem(int difficulty) {
        int a = generateRandomNumber(difficulty) + 1;
        int b = generateRandomNumber(difficulty) + 1;
        String boyName = hebrewBoysNames.get(random.nextInt(hebrewBoysNames.size()));
        int result = 0;
        String content, solution, explanation;

        switch (difficulty) {
            case LEVEL_ONE:
                String item1 = items1Names.get(random.nextInt(items1Names.size()));
                String item2 = items2Names.get(random.nextInt(items2Names.size()));

                result = a + b;

                content = String.format("%s שם %d %s %s ולאחר מכן הוסיף עוד %d. כמה %s יש ל%s עכשיו?", boyName, a, item2, item1, b, item2, boyName);
                solution = String.valueOf(result);
                explanation = String.format("ת. %d ועוד %d הם %d.", a, b, result);
                break;
            case LEVEL_TWO:
            default:
                String girlName = hebrewGirlsNames.get(random.nextInt(hebrewGirlsNames.size()));
                String fruitName = fruitNames.get(random.nextInt(fruitNames.size()));
                a += b;
                result = a - b;

                content = String.format("%s קנתה %d %s, אך נתנה %d ל%s. כמה %s נשאר לה?", girlName, a, fruitName, b, boyName, fruitName);
                solution = String.valueOf(result);
                explanation = String.format("ת. %d פחות %d הם %d", a, b, result);
                break;
        }

        return new Question(WORD_PROBLEM, content, difficulty, solution, explanation);
    }


    private static int generateRandomNumberPow(int difficulty) {
        return (int) (Math.random() * Math.pow(10, difficulty)) + 1;
    }

    private static int generateRandomNumber(int difficulty) {
        return (random.nextInt(1, 10)) * difficulty;
    }

    private static int generateRandomNumberForN(int difficulty) {
        return (random.nextInt(1, 10)) + difficulty;
    }

    private static int generateRandomForQuadEquNumber(int difficulty) {
        if (difficulty == 1){
            difficulty = generateRandomNumber(difficulty);
        }
        return random.nextInt(difficulty * 2 + 1) - difficulty;
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
        while (isValid) {
            System.out.println(generateQuadraticEquation(1));
            count--;
            if (count == 0) {
                isValid = false;
            }
        }
    }


}