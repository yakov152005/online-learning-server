package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;

@Service
public class DerivativeQuestionGenerator implements QuestionGeneratorStrategy {

    @Override
    public Question generateQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty); // מקדם ל x²
        int b = generateRandomNumber(difficulty); // מקדם ל x
        int c = generateRandomNumber(difficulty); // מספר חופשי
        int d = generateRandomNumber(difficulty); // מקדם במחנה (x + d)
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();


        switch (difficulty) {
            case LEVEL_ONE:
                content = "✦ Find the derivative of the function: *✦ f(x) = " + a + "x² + " + b + "x" +
                          "*✦ Please write the full equation "+ afterDerivativeFormula;
                solution = "f'(x)=" + (2 * a) + "x+" + b;

                steps.add(new SolutionStep(1, "חוק הנגזרת של חזקה: נגזרת של axⁿ היא n ✱ axⁿ⁻¹", "d/dx [axⁿ] = n ✱ axⁿ⁻¹"));
                steps.add(new SolutionStep(2, "חשב נגזרת של " + a + "x²", "d/dx [" + a + "x²] = " + (2 * a) + "x"));
                steps.add(new SolutionStep(3, "חשב נגזרת של " + b + "x", "d/dx [" + b + "x] = " + b));
                steps.add(new SolutionStep(4, "שלב את הנגזרות יחד", "f'(x)=" + (2 * a) + "x+" + b));

                explanation = String.join("\n",
                        "✦ Step 1:  השתמש בנוסחה הכללית לנגזרת של איקס בחזקה כלשהי. " + afterDerivativeAXFormula,
                        "*✦ Step 2: מצא את נגזרת הביטוי הראשון.",
                        "*✦ Step 3: מצא את נגזרת הביטוי השני.",
                        "*✦ Step 4: חבר את כל התוצאות לקבלת הנגזרת הסופית.  " + afterDerivativeFormula
                );
                break;

            case LEVEL_TWO:
                content = "✦ Find the derivative of the function: *✦ f(x) = " + c + "x³ + " + d + "x²" +
                          "*✦ Please write the full equation " + afterDerivativeFormula2;
                solution = "f'(x)=" + (3 * c) + "x²+" + (2 * d) + "x";

                steps.add(new SolutionStep(1, "חוק הנגזרת של חזקה: d/dx [axⁿ] = n * axⁿ⁻¹", ""));
                steps.add(new SolutionStep(2, "חשב נגזרת של " + c + "x³", "d/dx [" + c + "x³] = " + (3 * c) + "x²"));
                steps.add(new SolutionStep(3, "חשב נגזרת של " + d + "x²", "d/dx [" + d + "x²] = " + (2 * d) + "x"));
                steps.add(new SolutionStep(4, "שלב את הנגזרות יחד", solution));

                explanation = String.join("\n",
                        "✦ Step 1:  השתמש בנוסחה הכללית לנגזרת של איקס בחזקה כלשהי. " + afterDerivativeAXFormula,
                        "*✦ Step 2: מצא את נגזרת הביטוי הראשון.",
                        "*✦ Step 3: מצא את נגזרת הביטוי השני.",
                        "*✦ Step 4: חבר את כל התוצאות לקבלת הנגזרת הסופית.  " + afterDerivativeFormula2
                );
                break;

            case LEVEL_THREE:
                int p = generateRandomNumber(difficulty);
                content = "✦ מצא את הנגזרת של הפונקציה:"
                          + "*✦ f(x) = " + p + "x sin(x)"
                          + "*✦ Please write the full equation: f'(x) = ?";

                solution = "f'(x)=" + p + "cos(x)+" + p + "x" +"cos(x)";

                steps.add(new SolutionStep(1, "השתמש בכלל המכפלה: " + roleMultiDerivative,
                        "d/dx [ " + p + "x sin(x) ] = ","= d/dx [ \" + p + \"x ] sin(x) + \" + p + \"x d/dx [ sin(x) ]"));
                steps.add(new SolutionStep(2, "חשב את נגזרות כל אחד מהאיברים",
                        "d/dx [" + p + "x] = ", p + ", d/dx [sin(x)] = cos(x)"));
                steps.add(new SolutionStep(3, "הציב בתוצאה",
                        solution));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בכלל המכפלה." + roleMultiDerivative,
                        "*✦ step 2: חשב את נגזרת כל פונקציה בנפרד.",
                        "*✦ step 3: הצב את הנגזרות בנוסחה כדי לקבל את התוצאה הסופית."
                );
                break;
            case LEVEL_FOUR:
                int coef = generateRandomNumber(difficulty);
                content = "✦ מצא את הנגזרת של הפונקציה: "
                          + "*✦ f(x) = sin(" + coef + "x²)"
                          + "*✦ Please write the full equation: f'(x) = ?";

                solution = "f'(x)=" + (2 * coef) + "x" + "cos(" + coef + "x²)";

                steps.add(new SolutionStep(1, "השתמש בכלל השרשרת: " + chainRule,
                        "d/dx [sin(" + coef + "x²)] = cos(" + coef + "x²) * d/dx [" + coef + "x²]"));

                steps.add(new SolutionStep(2, "חשב את נגזרת הפונקציה הפנימית g(x) = " + coef + "x²",
                        "d/dx [" + coef + "x²] = " + (2 * coef) + "x"));

                steps.add(new SolutionStep(3, "שלב את התוצאות יחד",
                        solution));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בכלל השרשרת לנגזרת של פונקציה מורכבת: " + chainRule,
                        "*✦ step 2: חשב את נגזרת הפונקציה הפנימית.",
                        "*✦ step 3: הצב את הערכים כדי לקבל את הפונקציה הנגזרת."
                );
                break;
            case LEVEL_FIVE:
            default:
                content = "✦ מצא את הנגזרת של הפונקציה: "
                          + "*✦ f(x) = (" + a + "x² + " + b + "x + " + c + ") / (x + " + d + ")"
                          + "*✦ Please write the full equation: f'(x) = ?";

                solution = "f'(x)=[(" + (2 * a) + "x+" + b + ")✱(x+ " + d +")-(" + a + "x²+" + b + "x+" + c + ")✱1]/(x+" + d + ")²";

                steps.add(new SolutionStep(1, "השתמש בכלל המנה: " + dishRule,
                        "d/dx [(" + a + "x² + " + b + "x + " + c + ") / (x + " + d + ")] = [(x + " + d + ") d/dx [" + a + "x² + " + b + "x + " + c + "] - (" + a + "x² + " + b + "x + " + c + ") d/dx [x + " + d + "]] / (x + " + d + ")²"));

                steps.add(new SolutionStep(2, "חשב את נגזרות הפונקציות",
                        "d/dx [" + a + "x² + " + b + "x + " + c + "] = " + (2 * a) + "x + " + b + ", d/dx [x + " + d + "] = 1"));

                steps.add(new SolutionStep(3, "הצב את התוצאות בנוסחה",
                        solution));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בכלל המנה." + dishRule,
                        "*✦ step 2: חשב את נגזרות הפונקציות במונה ובמחנה.",
                        "*✦ step 3: הצב בנוסחה וקבל את התוצאה."
                );
                break;

        }

        return new Question(DERIVATIVE, content, difficulty, solution, explanation,steps);
    }
}
