package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomForQuadEquNumber;

@Service
public class QuadraticEquationQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
        int x1 = generateRandomForQuadEquNumber(difficulty);
        int x2 = generateRandomForQuadEquNumber(difficulty);
        int a = generateRandomForQuadEquNumber(difficulty);
        int b = -a * (x1 + x2);
        int c = a * x1 * x2;

        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();

        switch (difficulty){
            case LEVEL_ONE:
                content = "A = " + a + ", B = " + b + ", C = " + c + " *✦ Find X₁,₂ and write down the largest X among them.";
                solution = String.valueOf(Math.max(x1, x2));

                steps.add(new SolutionStep(1, "השתמש בנוסחת השורשים:",
                        "X₁,₂ = (-b ± √Δ) / 2a"));
                steps.add(new SolutionStep(2, "חשב את הדיסקרימיננט (Δ):",
                        "Δ = b² - 4ac = (" + b + ")² - 4 * (" + a + ") * (" + c + ")"));
                int delta = (b * b) - (4 * a * c);
                steps.add(new SolutionStep(3, "חשב את ערך הדיסקרימיננט:",
                        "Δ = " + delta));
                steps.add(new SolutionStep(4, "חשב את השורשים של המשוואה הריבועית:",
                        "X₁ = (-" + b + " + √" + delta + ") / (2 * " + a + ")",
                        "X₂ = (-" + b + " - √" + delta + ") / (2 * " + a + ")"));
                steps.add(new SolutionStep(5, "חשב את ערכי השורשים:",
                        "X₁ = " + x1,
                        "X₂ = " + x2));
                steps.add(new SolutionStep(6, "מצא את השורש הגדול ביותר:",
                        "max(X₁, X₂) = " + Math.max(x1, x2)));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת השורשים: " + quadraticFormula,
                        "*✦ step 2: חישוב הדלתא (Δ): " + deltaFormula,
                        "*✦ step 3: חישוב שורשים: X₁ = (-b + √Δ) / 2a, X₂ = (-b - √Δ) / 2a",
                        "*✦ step 4: כתוב את השורש הגדול ביותר מביניהם"
                );
                break;
            case LEVEL_TWO:
            default:
                content = "A = " + a + ", B = " + b + ", C = " + c + " *✦ מצא את נקודת הקיצון של הפונקציה.";
                solution = "(" + (-b / (2 * a)) + "," + ((4 * a * c - (b * b)) / (4 * a)) + ")";

                steps.add(new SolutionStep(1, "מצא את שיעור ה-X של נקודת הקיצון (קודקוד הפרבולה):",
                        "Xv = -b / 2a = (-" + b + ") / (2 * " + a + ")"));

                int xv = -b / (2 * a);
                steps.add(new SolutionStep(2, "חשב את שיעור ה-X של נקודת הקיצון:",
                        "Xv = " + xv));

                steps.add(new SolutionStep(3, "מצא את שיעור ה-Y של נקודת הקיצון על ידי הצבת Xv בפונקציה:",
                        "Yv = a * (Xv)^2 + b * Xv + c"));

                int yv = (a * xv * xv) + (b * xv) + c;
                steps.add(new SolutionStep(4, "חשב את שיעור ה-Y של נקודת הקיצון:",
                        "Yv = " + yv));

                steps.add(new SolutionStep(5, "מצא את נקודת הקיצון:",
                        "(" + xv + "," + yv + ")"));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת קודקוד הפרבולה: Xv = -b / 2a",
                        "*✦ step 2: הצב את הנתונים וחשב את ערך Xv.",
                        "*✦ step 3: הצב את Xv בפונקציה כדי למצוא את Yv.",
                        "*✦ step 4: מצא את נקודת הקיצון של הפרבולה: (Xv, Yv)."
                );
                break;

        }
        return new Question(QUADRATIC_EQUATION,content,difficulty,solution,explanation,steps);
    }

}
