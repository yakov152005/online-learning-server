package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.Constants.Question.EQUATION_LINE;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;

@Service
public class EquationOfTheLineQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
        int x1, y1, x2, y2, m;
        do {
            x1 = generateRandomNumber(difficulty);
            y1 = generateRandomNumber(difficulty);
            x2 = generateRandomNumber(difficulty);
            y2 = generateRandomNumber(difficulty);
        } while (x1 == x2 || (y2 - y1) % (x2 - x1) != 0);

        m = (y2 - y1) / (x2 - x1);
        int b = y1 - (m * x1);
        String result = "y=" + m + "x" + (b >= 0 ? "+" + b : "-" + Math.abs(b));

        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();


        switch (difficulty) {
            case LEVEL_ONE:
                content = "x₁,y₁ = (" + x1 + "," + y1 + "),  x₂,y₂ = (" + x2 + "," + y2 + ")  *✦ m = ?";
                solution = String.valueOf(m);

                steps.add(new SolutionStep(1, "השתמש בנוסחת השיפוע:",
                        findMFormula));
                steps.add(new SolutionStep(2, "הצב את הערכים הנתונים:",
                        "m = (" + y2 + " - " + y1 + ") / (" + x2 + " - " + x1 + ")"));
                steps.add(new SolutionStep(3, "חשב את התוצאה:",
                        "m = " + (y2 - y1) + " / " + (x2 - x1)));
                steps.add(new SolutionStep(4, "בצע את החילוק ומצא את השיפוע:",
                        "m = " + m));

                explanation = String.join("\n",
                        "✦ step 1:הצב את הערכים הנתונים לפי הסדר במשוואת השיפוע : " + findMFormula,
                        "*✦ step 2: צמצם כל חלק בנפרד על ידי חיסור/חיבור התוצאה",
                        "*✦ step 3: חלק את התוצאה",
                        "*✦ step 4: רשום את התשובה"
                );
                break;
            case LEVEL_TWO:
                content = "x₁,y₁ = (" + x1 + "," + y1 + "),  m = " + m + " *✦ Find the " + mathFormula +
                          " *✦ Please write the full equation " + mathFormula;
                solution = result;


                steps.add(new SolutionStep(1, "השתמש במשוואת הישר:",
                        mathFormula));
                steps.add(new SolutionStep(2, "הצב את ערכי x₁, y₁ והשיפוע m כדי למצוא את b:",
                        y1 + " = " + m + " * " + x1 + " + b"));
                steps.add(new SolutionStep(3, "חשב את b על ידי העברת אגפים:",
                        "b = " + y1 + " - (" + m + " * " + x1 + ")"));

                steps.add(new SolutionStep(4, "מצא את b:",
                        "b = " + b));

                steps.add(new SolutionStep(5, "הרכב את משוואת הישר:",
                        result));

                explanation = String.join("\n",
                        "✦ step 1: הצב את הערכים במשוואת קו הישר: " + equationLineFormula,
                        "*✦ step 2: פתח סוגריים והעבר אגפים",
                        "*✦ step 3: כתוב את התוצאה בצורה הזאת: " + mathFormula
                );
                break;
            case LEVEL_THREE:
            default:
                content = "x₁,y₁ = (" + x1 + "," + y1 + "), x₂,y₂ = (" + x2 + "," + y2 + ") *✦ Find the y=mx+b" +
                          " *✦ Please write the full equation y=mx+b";
                solution = result;

                steps.add(new SolutionStep(1, "השתמש בנוסחת השיפוע:",
                        mathFormula));
                steps.add(new SolutionStep(2, "הצב את הערכים הנתונים:",
                        "m = (" + y2 + " - " + y1 + ") / (" + x2 + " - " + x1 + ")"));
                steps.add(new SolutionStep(3, "חשב את התוצאה:",
                        "m = " + m));
                steps.add(new SolutionStep(4, "השתמש במשוואת הישר y = mx + b והצב את x₁, y₁ והמקדמים כדי למצוא את b:",
                        y1 + " = " + m + " * " + x1 + " + b"));
                steps.add(new SolutionStep(5, "חשב את b על ידי העברת אגפים:",
                        "b = " + y1 + " - (" + m + " * " + x1 + ")"));
                steps.add(new SolutionStep(6, "מצא את b:",
                        "b = " + b));
                steps.add(new SolutionStep(7, "הרכב את משוואת הישר:",
                        result));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחא למציאת השיפוע : " + findMFormula,
                        "*✦ step 2: הצב את הערכים  במשוואת קו הישר: " + equationLineFormula,
                        "*✦ step 3: פתח סוגריים והעבר אגפים",
                        "*✦ step 4: כתוב את התוצאה בצורה הזאת: " + mathFormula
                );
                break;
        }

        return new Question(EQUATION_LINE, content, difficulty, solution, explanation,steps);
    }
}
