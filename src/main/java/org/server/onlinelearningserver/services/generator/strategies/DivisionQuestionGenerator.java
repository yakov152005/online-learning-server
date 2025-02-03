package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.Constants.Question.DIV;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;

@Service
public class DivisionQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        int result = 0;

        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();

        switch (difficulty) {
            case LEVEL_ONE:
                b *= a;
                content = b + " ➗ " + a + " = ?";
                solution = String.valueOf(b / a);

                steps.add(new SolutionStep(1, "זהה את שני המספרים שיש לחלק.",
                        "חלק את " + b + " ב-" + a));

                steps.add(new SolutionStep(2, "בצע את פעולת החילוק.",
                        b + " ➗ " + a + " = " + (b / a)));

                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "התשובה היא: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התרגיל, חלק את המספר הראשון במספר השני.",
                        "*✦ step 2: בצע את החילוק: " + b + " ➗ " + a,
                        "*✦ step 3: רשום את התוצאה הסופית."
                );
                break;
            case LEVEL_TWO:
                b = a * ((int) (Math.random() * Math.pow(4, difficulty)));
                content = b + " ➗ " + a + " = ?";
                solution = String.valueOf(b / a);

                steps.add(new SolutionStep(1, "זהה את שני המספרים שיש לחלק.",
                        "חלק את " + b + " ב-" + a));
                steps.add(new SolutionStep(2, "בצע את פעולת החילוק.",
                        b + " ➗ " + a + " = " + (b / a)));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "התשובה היא: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: חלק את המספר הגדול במספר הקטן.",
                        "*✦ step 2: בצע את פעולת החילוק וקבל את התוצאה.",
                        "*✦ step 3: רשום את התוצאה הסופית."
                );
                break;
            case LEVEL_THREE:
                result = generateRandomNumber(difficulty);
                a = b * result;
                content = "X ➗ " + b + " = " + result + " *✦ X = ?";
                solution = String.valueOf(a);

                steps.add(new SolutionStep(1, "בודד את X והעבר את האגפים.",
                        "X = " + result + " ✱ " + b));
                steps.add(new SolutionStep(2, "בצע את פעולת הכפל כדי לבודד את X.",
                        "X = " + (result * b)));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית של X.",
                        "X = " + solution));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את X, יש להעביר את המספר שמשמאל לאיקס לאגף השני.",
                        "*✦ step 2: המרה של חילוק לכפל: X = " + result + " ✱ " + b,
                        "*✦ step 3: בצע את פעולת הכפל וקבל את התוצאה."
                );
                break;

            case LEVEL_FOUR:
                result = (int) (Math.random() * Math.pow(3, difficulty)) + 1;
                a = b * result;
                content = "X ➗ " + b + " = " + result + " *✦ X = ?";
                solution = String.valueOf(a);

                steps.add(new SolutionStep(1, "בודד את X במשוואה.",
                        "X = " + result + " ✱ " + b));
                steps.add(new SolutionStep(2, "בצע את פעולת הכפל כדי לבודד את X.",
                        "X = " + (result * b)));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית של X.",
                        "X = " + solution));

                explanation = String.join("\n",
                        "✦ step 1: בידוד X בעזרת מעבר אגפים.",
                        "*✦ step 2: המרה של חילוק לכפל: X = " + result + " ✱ " + b,
                        "*✦ step 3: בצע את פעולת הכפל וקבל את התוצאה."
                );
                break;
            case LEVEL_FIVE:
            default:
                result = (int) (Math.random() * Math.pow(10, difficulty)) + 1;
                a = b * result;
                content = "X ➗ " + b + " = " + result + " *✦ X = ?";
                solution = String.valueOf(a);

                steps.add(new SolutionStep(1, "בודד את X במשוואה.",
                        "X = " + result + " ✱ " + b));
                steps.add(new SolutionStep(2, "בצע את פעולת הכפל כדי לבודד את X.",
                        "X = " + (result * b)));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית של X.",
                        "X = " + solution));

                explanation = String.join("\n",
                        "✦ step 1: בידוד X בעזרת מעבר אגפים.",
                        "*✦ step 2: המרה של חילוק לכפל: X = " + result + " ✱ " + b,
                        "*✦ step 3: בצע את פעולת הכפל וקבל את התוצאה."
                );
                break;
        }

        return new Question(DIV, content, difficulty, solution, explanation,steps);
    }
}
