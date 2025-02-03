package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.Constants.Question.MULTI;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumberPow;

@Service
public class MultiplicationQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();


        switch (difficulty) {
            case LEVEL_ONE:
                content = a + " ✱ " + b + " = ?";
                solution = String.valueOf(a * b);

                steps.add(new SolutionStep(1, "זהה את שני המספרים שיש לכפול.",
                        "כפול את " + a + " ב-" + b));

                steps.add(new SolutionStep(2, "בצע את פעולת הכפל.",
                        a + " ✱ " + b + " = " + (a * b)));

                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "התשובה היא: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התרגיל הזה פשוט הכפל את המספר הראשון במספר השני ",
                        "*✦ step 2: " +  a + " ✱ " + b,
                        "*✦ step 3: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_TWO:
                int result = a * generateRandomNumberPow(difficulty);
                b = result / a;
                content = a + " ✱ " + "X" + " = " + result + " *X = ?";
                solution = String.valueOf(b);

                steps.add(new SolutionStep(1, "העבר את המספר שמשמאל לאיקס אגף.",
                        a + "X = " + result + " ➝ X = " + result + " ÷ " + a));
                steps.add(new SolutionStep(2, "בצע את פעולת החילוק כדי לבודד את X.",
                        "X = " + (result / a)));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית של X.",
                        "X = " + solution));

                explanation = String.join("\n",
                        "✦ step 1: כדי למצוא את X, יש להעביר את המספר שמשמאל לאיקס לאגף השני.",
                        "*✦ step 2: המרה של כפל לחילוק: X = " + result + " ÷ " + a,
                        "*✦ step 3: בצע את החילוק וקבל את התוצאה."
                );
                break;
            case LEVEL_THREE:
            default:
                int x = generateRandomNumber(difficulty);
                int y = generateRandomNumber(difficulty);
                int result2 = (a * x) + (b * y);

                content = a + "X + " + b + "Y = " + result2 + " *✦ find X and Y *✦ Write the answer as X=? Y=? ";
                solution = "X=" + x + " Y =" + y;

                steps.add(new SolutionStep(1, "זהה את המשוואה ואת שני הנעלמים.",
                        a + "X + " + b + "Y = " + result2));
                steps.add(new SolutionStep(2, "מצא ערכים אפשריים עבור X ו-Y שמתאימים למשוואה.",
                        "X = " + x + ", Y = " + y));
                steps.add(new SolutionStep(3, "בדוק את ההצבה במשוואה.",
                        a + "(" + x + ") + " + b + "(" + y + ") = " + result2));
                steps.add(new SolutionStep(4, "אם התוצאה נכונה, רשום את הפתרון הסופי.",
                        "X = " + x + ", Y = " + y));

                explanation = String.join("\n",
                        "✦ step 1: מצא שני ערכים שמתאימים למשוואה.",
                        "*✦ step 2: הצב ערכים ל-איקס ו-וואי ובדוק שהם מספקים את המשוואה.",
                        "*✦ step: אשר את הפתרון הסופי."
                );
                break;

        }

        return new Question(MULTI, content, difficulty, solution, explanation,steps);
    }
}
