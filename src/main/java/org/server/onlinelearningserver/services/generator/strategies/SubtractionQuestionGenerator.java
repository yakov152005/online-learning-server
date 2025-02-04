package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.RandomUtils.*;

@Service
public class SubtractionQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();

        switch (difficulty) {
            case LEVEL_ONE:
                content = a + " ➖ " + b + " = ?";
                solution = calculateResult(a, b, "-");

                steps.add(new SolutionStep(1, "חסר את המספר השני מהמספר הראשון", a + " - " + b));
                steps.add(new SolutionStep(2, "חשב את התוצאה", a + " - " + b + " = " + solution));
                steps.add(new SolutionStep(3, "רשום את התשובה הסופית", "תוצאה: "," " + solution));

                explanation = String.join("\n",
                        "✦ step 1: בשלב זה אתה מחסר את המספר השני מהמספר הראשון כדי למצוא את הסכום. ",
                        "*✦ step 2: ראשית, זהה את המספר שיש לחסר: " + a + " - " + b,
                        "*✦ step 3:בצע את החישוב בפועל, ואז רשום את התוצאה."
                );
                break;
            case LEVEL_TWO:
                a += generateRandomNumber(difficulty);
                b += generateRandomNumber(difficulty);
                content = a + " ➖ " + b + " = ?";
                solution = calculateResult(a, b, "-");

                steps.add(new SolutionStep(1, "חסר את המספר השני מהמספר הראשון", a + " - " + b));
                steps.add(new SolutionStep(2, "חשב את התוצאה", a + " - " + b + " = " + solution));
                steps.add(new SolutionStep(3, "רשום את התשובה הסופית", "תוצאה: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: בשלב זה אתה מבצע חיסור עם מספרים גדולים יותר.",
                        "*✦ step 2: ראשית, זהה את המספר שיש לחסר: " + a + " - " + b,
                        "*✦ step 3: בשלב זה אתה מחסר את המספר השני מהמספר הראשון כדי למצוא את הסכום.",
                        "*✦ step 4:בצע את החישוב בפועל, ואז רשום את התוצאה."
                );
                break;
            case LEVEL_THREE:
                int num = generateRandomNumber(difficulty);
                int num2 = generateRandomNumber(difficulty);
                int x = generateRandomNumber(difficulty);
                solution = calculateResult(a, b, "-");

                List<String> options = new ArrayList<>();
                options.add(String.valueOf(num));
                options.add(String.valueOf(num2));
                options.add(String.valueOf(x));
                options.add(String.valueOf(solution));

                Collections.shuffle(options);

                content = String.join("\n",
                        a + " ➖ " + b + " = ?",
                        "*✦ What is the right solution?",
                        "*• " + options.get(0),
                        "*• " + options.get(1),
                        "*• " + options.get(2),
                        "*• " + options.get(3)
                );


                steps.add(new SolutionStep(1, "חסר את המספר השני מהמספר הראשון", a + " - " + b));
                steps.add(new SolutionStep(2, "חשב את התוצאה", "תוצאה: " + solution));
                steps.add(new SolutionStep(3, "בחר את התשובה הנכונה מבין האפשרויות", "נבחרה התשובה: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: חסר את המספר השני מהמספר הראשון ",
                        "*✦ step 2: " + a + " - " + b,
                        "*✦ step 3: חשב את התוצאה",
                        "*✦ step 4: בחור את התשובה הסופית מהתשובות בחירה , ורשום את המספר של התוצאה"
                );

                break;
            case LEVEL_FOUR:
            default:
                a = generateRandomNumberPow(difficulty);
                int result = generateRandomNumberPow(difficulty);
                b = a - result;
                content = a + " ➖ " + "X" + " = " + b + " *✦ X = ?";
                solution = String.valueOf(result);

                steps.add(new SolutionStep(1, "העבר את האיבר הימני של המשוואה לאגף השני", a + " - X = " + b));
                steps.add(new SolutionStep(2, "הוסף את X לצד ימין והעבר את המספר לאגף השני", "-X = " + (b - a)));
                steps.add(new SolutionStep(3, "חלק את שני הצדדים ב-(-1) כדי לבטל את הסימן השלילי", "-X = " + (b - a) + "/(-1)" ));
                steps.add(new SolutionStep(3, "התשובה הסופית היא:", "X = " + (b - a) * -1));

                explanation = String.join("\n",
                        "✦ step 1: .עליך להעביר את המספר הקבוע לאגף השני ",
                        "*✦ step 2: לשנות סימן בהתאם לכללי האלגברה.",
                        "*✦ step 3: .לאחר מכן לחלק במקדם של איקס כדי למצוא את הפתרון",
                        "*✦ step 4: .רשום את התשובה הסופית של כמה איקס שווה שיצאה לך במספרים"
                );

                break;
        }
        return new Question(SUB, content, difficulty, solution, explanation,steps);
    }
}