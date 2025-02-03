package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
            case EQUATION_LINE -> generateEquationOfTheLine(difficulty);
            case DERIVATIVE -> generateDerivativeQuestion(difficulty);
            //case "fractions" -> generateFractionQuestion(difficulty);
            default -> throw new IllegalArgumentException("Unknown question type: " + category);
        };
    }

    private static Question generateDerivativeQuestion(int difficulty) {
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


    private static Question generateEquationOfTheLine(int difficulty) {
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


    private static Question generateQuadraticEquation(int difficulty){
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

    private static Question generateInvoiceSeries(int difficulty) {
        int a1 = generateRandomNumber(difficulty);
        int d = generateRandomNumber(difficulty);
        int n = generateRandomNumberForN(difficulty);
        int an = a1 + ((n - 1) * d);
        int Sn = (n * (2 * a1 + ((n-1) * d))) / 2;
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();

        switch (difficulty) {
            case LEVEL_ONE:
                content = "a1 = " + a1 + ", d = " + d + " *✦ a" + n + " = ?";
                solution = String.valueOf(an);

                steps.add(new SolutionStep(1, "השתמש בנוסחת האיבר הכללי של סדרה חשבונית", anFormula));
                steps.add(new SolutionStep(2, "הצב את הערכים הנתונים", "a" + n + " = " + a1 + " + (" + (n - 1) + " ✱ " + d + ")"));
                steps.add(new SolutionStep(3, "חשב את התוצאה", "a" + n + " = " + an));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת מציאת איבר ה : " + anFormula,
                        "*✦ step 2: הצב את הערכים הנתונים לך ",
                        "*✦ step 3: חישוב כל חלק בנפרד כולל פתיחת סוגריים ",
                        "*✦ step 4: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_TWO:
                content = " an = " + an + ", d = " + d + ", n = " + n + " *✦ a1 = ?";
                solution = String.valueOf(a1);

                steps.add(new SolutionStep(1, "השתמש בנוסחת האיבר הכללי", anFormula));
                steps.add(new SolutionStep(2, "העבר אגפים כדי למצוא את a1", "a1 = an - (n-1) ✱ d"));
                steps.add(new SolutionStep(3, "הצב את הערכים", "a1 = " + an + " - (" + (n - 1) + " ✱ " + d + ")"));
                steps.add(new SolutionStep(4, "חשב את התוצאה", "a1 = " + a1));

                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת מציאת איבר ה : " + anFormula,
                        "*✦ step 2: הצב את הערכים הנתונים לך ",
                        "*✦ step 3: שים לב הפעם צריך למצוא את איבר הראשון אז בצע העברת אגפים ובודד את האיבר הראשון",
                        "*✦ step 4: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_THREE:
                int n2 = generateRandomNumberForN(difficulty);
                int an2 = a1 + ((n2 - 1) * d);

                content = "a" + n + " = " + an + ", a" + n2 + " = " + an2 + " *✦ a1 = ? " + ", d = ?" +
                          " *✦ You should write the answer for a1 and not for d";
                solution = String.valueOf(a1);

                steps.add(new SolutionStep(1, "השתמש בנוסחאות הבאות עבור שני האיברים",
                        "[a" + n + " = a1 + (" + n + "-1) ✱ d] ➖ [a" + n2 + " = a1 + (" + n2 + "-1) ✱ d]"));
                steps.add(new SolutionStep(2, "חסר בין שתי המשוואות כדי לבטל את a1",
                        "(" + an + " - " + an2 + ") = ((" + n + " - 1) - (" + n2 + " - 1)) ✱ d"));
                steps.add(new SolutionStep(3, "מצא את d על ידי חלוקה בהפרש האינדקסים",
                        "d = (" + (an - an2) + ") / (" + (n - n2) + ") = " + d));
                steps.add(new SolutionStep(4, "מצא את a1 על ידי הצבה בנוסחת האיבר הכללי",
                        "a1 = " + an + " - (" + (n - 1) + " ✱ " + d + ") = " + a1));

                explanation = String.join("\n",
                        "✦ step 1: הצב בנוסחאות : " +
                        "*[a" + n + " = a1 + (" + n + " - 1) ✱ d]",
                        "*➖",
                        "*[a" + n2 + " = a1 + (" + n2 + " - 1) ✱ d]",
                        "*✦ step 2: צמצם את המשוואות ואז תחסר את האיבר הראשון כדי למצוא את הדילוגים ",
                        "*✦ step 3: אחרי שמצאת את הדילוגים, תוכל למצוא את האיבר הראשון בקלות על ידי הצבה בנוסחה: " + anFormula ,
                        "*✦ step 4: רשום את התשובה הסופית של ה a1 שיצאה לך. "
                );
                break;
            case LEVEL_FOUR:
                content = " a1 = " + a1 + ", d = " + d + ", n = " + n + " *✦ S" + n + " = ?";
                solution = String.valueOf(Sn);

                steps.add(new SolutionStep(1, "השתמש בנוסחת סכום סדרה חשבונית", snFormula));
                steps.add(new SolutionStep(2, "הצב את הערכים הנתונים", "S" + n + " = (" + n + " ✱ (2 ✱ " + a1 + " + (" + (n - 1) + " ✱ " + d + "))) / 2"));
                steps.add(new SolutionStep(3, "חשב את התוצאה", "S" + n + " = " + Sn));


                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת מציאת סכום ה : " + snFormula,
                        "*✦ step 2: הצב את הערכים הנתונים לך ",
                        "*✦ step 3: חשב את התוצאה",
                        "*✦ step 4: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_FIVE:
            default:
                content = " a1 = " + a1 + ", d = " + d + ", Sn" + " = " + Sn + " *✦ n = ?" +
                          " *✦ Remember n cannot be a negative or decimal value.";
                solution = String.valueOf(n);

                steps.add(new SolutionStep(1, "השתמש בנוסחת סכום סדרה חשבונית",
                        snFormula));

                steps.add(new SolutionStep(2, "כפול את שני אגפי המשוואה ב-2 כדי להיפטר מהמכנה",
                        "2 ✱ Sn = n ✱ [(2 ✱ a1 + (n - 1) ✱ d)]",
                        "2 ✱ " + Sn + " = n ✱ [" + (2 * a1) + " + (n - 1) ✱ " + d + "]"));

                steps.add(new SolutionStep(3, "פתח את הסוגריים באגף ימין",
                        (2 * Sn) + " = n ✱ (" + (2 * a1) + " + " + d +"n " + (-1 * d) + ")"));

                steps.add(new SolutionStep(4, "סדר את המשוואה לצורת משוואה ריבועית",
                        d + "n^2 + " + (2 * a1 - d) + "n - " + (2 * Sn) + " = 0"));

                steps.add(new SolutionStep(5, "זהה את המקדמים של המשוואה הריבועית:",
                        "A = " + d + ", B = " + (2 * a1 - d) + ", C = " + (-2 * Sn)));

                steps.add(new SolutionStep(6, "חשב את הדיסקרימיננטה באמצעות הנוסחה:",
                        "Δ = B^2 - 4AC",
                        "Δ = (" + (2 * a1 - d) + ")^2 - 4 ✱ " + d + " ✱ (" + (-2 * Sn) + ")"));

                steps.add(new SolutionStep(7, "הוצא שורש מהדיסקרימיננטה",
                        "√Δ = " + Math.sqrt(((2 * a1 - d) * (2 * a1 - d)) + (8 * d * Sn))));

                steps.add(new SolutionStep(8, "הציבו את הערכים בנוסחת השורשים:",
                        "n = (-B ± √Δ) / (2A)",
                        "n = (-(" + (2 * a1 - d) + ") ± " + Math.sqrt(((2 * a1 - d) * (2 * a1 - d)) + (8 * d * Sn)) + ") / (2 ✱ " + d + ")"));

                steps.add(new SolutionStep(9, "חשב את שני הפתרונות של  n₁,₂:",
                        "n₁ = (" + (-1 * (2 * a1 - d)) + " + " + Math.sqrt(((2 * a1 - d) * (2 * a1 - d)) + (8 * d * Sn)) + ") / " + (2 * d),
                        "n₂ = (" + (-1 * (2 * a1 - d)) + " - " + Math.sqrt(((2 * a1 - d) * (2 * a1 - d)) + (8 * d * Sn)) + ") / " + (2 * d)));

                steps.add(new SolutionStep(10, "בחר את הפתרון החיובי עבור n₁,₂",
                        "n = " + n));


                explanation = String.join("\n",
                        "✦ step 1: השתמש בנוסחת מציאת סכום ה : " + snFormula,
                        "*✦ step 2: הצב את הערכים הנתונים לך , חשב את הערכים והעבר אגפים ",
                        "*✦ step 3:כאשר '?' מייצג מספר חופשי A=?n^2 B=?n C=?  סדר לפי",
                        "*✦ step 4: n הצב את הערכים במשוואה ריבועית כדי למצוא את ה " + quadraticForInvoiceFormula,
                        "*✦ step 5: הטובה כלומר לא שלילית ולא עשרונית n רשום בתשובה רק את התוצאה של "

                );
                break;
        }
        return new Question(INVOICE_SERIES, content, difficulty, solution, explanation,steps);
    }

    private static Question generateAdditionQuestion(int difficulty) {
        int a = generateRandomNumber(difficulty);
        int b = generateRandomNumber(difficulty);
        int result,x,num,num2 = 0;
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();

        switch (difficulty) {
            case LEVEL_ONE:
                content = a + " ➕ " + b + " = ?";
                solution = calculateResult(a, b, "+");

                steps.add(new SolutionStep(1, "זהה את שני המספרים שעליך לחבר", a + " ו- " + b));
                steps.add(new SolutionStep(2, "בצע את פעולת החיבור", a + " + " + b + " = " + solution));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית", "התשובה: " + solution));

                explanation = String.join("\n",
                        "✦ step 1: בשלב זה אתה מחבר את שני המספרים יחד כדי למצוא את הסכום. ",
                        "*✦ step 2: ראשית, זהה את המספרים שיש לחבר: " + a + " + " + b,
                        "*✦ step 3:בצע את החישוב בפועל, ואז רשום את התוצאה."
                );
                break;
            case LEVEL_TWO:
                a += generateRandomNumber(difficulty);
                b += generateRandomNumber(difficulty);
                content = a + " ➕ " + b + " = ?";
                solution = calculateResult(a, b, "+");

                steps.add(new SolutionStep(1, "זהה את שני המספרים שעליך לחבר", a + " ו- " + b));
                steps.add(new SolutionStep(2, "בצע את פעולת החיבור", a + " + " + b + " = " + solution));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית", "התשובה: " + solution));


                explanation = String.join("\n",
                        "✦ step 1: בשלב זה אתה מבצע חיבור עם מספרים גדולים יותר.",
                        "*✦ step 2: ראשית, זהה את המספרים שיש לחבר: " + a + " + " + b,
                        "*✦ step 3:בצע את החישוב בפועל, ואז רשום את התוצאה."
                );
                break;
            case LEVEL_THREE:
                num = generateRandomNumber(difficulty);
                num2 = generateRandomNumber(difficulty);
                x = generateRandomNumber(difficulty);
                solution = calculateResult(a, b, "+");

                List<String> options = new ArrayList<>();
                options.add(String.valueOf(num));
                options.add(String.valueOf(num2));
                options.add(String.valueOf(x));
                options.add(String.valueOf(solution));

                Collections.shuffle(options);

                content = String.join("\n",
                        a + " ➕ " + b + " = ?",
                        "*✦ What is the right solution?",
                        "*• " + options.get(0),
                        "*• " + options.get(1),
                        "*• " + options.get(2),
                        "*• " + options.get(3)
                );

                steps.add(new SolutionStep(1, "חשב את הסכום על ידי חיבור המספרים", a + " + " + b));
                steps.add(new SolutionStep(2, "זהה את התשובה הנכונה מתוך האפשרויות", "נבחרה התשובה: " + solution));


                explanation = String.join("\n",
                        "✦ step 1: .בשלב זה אתה מבצע חיבור עם מספרים גדולים עוד יותר",
                        "*✦ step 2: ראשית, זהה את המספרים שיש לחבר: " + a + " + " + b,
                        "*✦ step 3: עליך לחשב את התוצאה ולאחר מכן לבחור את התשובה הנכונה מבין האפשרויות."
                );
                break;
            case LEVEL_FOUR:
                result = a + generateRandomNumber(difficulty);
                content = a + " ➕ " + "X" + " = " + result + " *✦ X = ?";
                solution = calculateResult(result, a, "-");

                steps.add(new SolutionStep(1, "העבר את המספר לצד השני של המשוואה על ידי ביצוע חיסור", result + " - " + a));
                steps.add(new SolutionStep(2, "חשב את הערך של X", "X = " + solution));


                explanation = String.join("\n",
                        "✦ step 1:  בשלב זה, עלייך למצוא את הערך של איקס על ידי בידודו במשוואה.",
                        "*✦ step 2: תחילה העבר את המספר הנוסף לאגף השני, על ידי ביצוע חיסור.",
                        "*✦ step 3: קבל את הערך של איקס",
                        "*✦ step 4: רשום את התשובה הסופית שיצאה לך במספרים"
                );

                break;
            case LEVEL_FIVE:
                x = generateRandomNumber(2);
                num = generateRandomNumber(2);
                a = generateRandomNumber(2);
                result = (num*x) + a;
                content = num + "X ➕ " + a + " = " + result + " *✦ X = ?";
                solution = String.valueOf(x);

                steps.add(new SolutionStep(1, "העבר את המספר הקבוע לצד השני של המשוואה", result + " - " + a));
                steps.add(new SolutionStep(2, "חלק את שני האגפים במקדם של X", "X = (" + result + " - " + a + ") / " + num));
                steps.add(new SolutionStep(3, "מצא את התוצאה", "X = " + solution));


                explanation = String.join("\n",
                        "✦ step 1: .עליך להעביר את המספר הקבוע לאגף השני ",
                        "*✦ step 2: לשנות סימן בהתאם לכללי האלגברה.",
                        "*✦ step 3: .לאחר מכן לחלק במקדם של איקס כדי למצוא את הפתרון",
                        "*✦ step 4: .רשום את התשובה הסופית של כמה איקס שווה שיצאה לך במספרים"
                );
                break;
            case LEVEL_SIX:
            default:
                x = generateRandomNumber(2);
                num = generateRandomNumber(2);
                num2 = generateRandomNumber(3);
                a = generateRandomNumber(2);
                result = (num*x) + (num2*x) + a;
                content = num + "X ➕ " + a + " ➕ " + num2 + "X = " + result + " *✦ X = ?";
                solution = String.valueOf(x);


                steps.add(new SolutionStep(1, "העבר את כל הביטויים עם X לצד אחד ואת המספרים לצד השני",
                        num + "X + " + num2 + "X = " + result + " - " + a));
                steps.add(new SolutionStep(2, "פשט את הביטוי – חבר מקדמים של X וחבר מספרים חופשיים",
                        (num + num2) + "X = " + (result - a)));
                steps.add(new SolutionStep(3, "חלק את שני הצדדים במקדם של X כדי למצוא את X",
                        "X = " + (result - a) + " ÷ " + (num + num2)));
                steps.add(new SolutionStep(4, "קבל את התוצאה הסופית", "X = " + solution));


                explanation = String.join("\n",
                        "✦ step 1: העבר אגפים את האיקסים בצד אחד והמספרים החופשיים בצד שני ",
                        "*✦ step 2: צמצם - חבר מקדמים של איקס, ומספרים חופשיים",
                        "*✦ step 3: כדי למצוא את הפתרון, חלק במקדם של איקס",
                        "*✦ step 4: קבל את התוצאה הסופית "
                );
                break;
        }
        return new Question(ADD, content, difficulty, solution, explanation, steps);
    }

    private static Question generateSubtractionQuestion(int difficulty) {
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
                steps.add(new SolutionStep(3, "רשום את התשובה הסופית", "תוצאה: " + solution));

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

    private static Question generateMultiplicationQuestion(int difficulty) {
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


    private static Question generateDivisionQuestion(int difficulty) {
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

    private static Question generateWordProblem(int difficulty) {
        int a = generateRandomNumber(difficulty) + 1;
        int b = generateRandomNumber(difficulty) + 1;
        String boyName = hebrewBoysNames.get(random.nextInt(hebrewBoysNames.size()));
        String girlName = hebrewGirlsNames.get(random.nextInt(hebrewGirlsNames.size()));
        String fruitName = fruitNames.get(random.nextInt(fruitNames.size()));
        String item1 = items1Names.get(random.nextInt(items1Names.size()));
        String item2 = items2Names.get(random.nextInt(items2Names.size()));

        int result = 0;
        String content, solution, explanation;
        List<SolutionStep> steps = new ArrayList<>();


        switch (difficulty) {
            case LEVEL_ONE:
                result = a + b;

                content = String.format("%s שם %d %s %s ולאחר מכן הוסיף עוד %d. * כמה %s יש ל%s עכשיו?", boyName, a, item2, item1, b, item2, boyName);
                solution = String.valueOf(result);

                steps.add(new SolutionStep(1, "זהה את המספרים שעליך לחבר.",
                        "חבר את " + a + " ו-" + b));
                steps.add(new SolutionStep(2, "בצע את פעולת החיבור.",
                        a + " + " + b + " = " + result));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "כמות ה" + item2 + " שיש ל-" + boyName + " עכשיו היא " + result));
                steps.add(new SolutionStep(4, "תשובה סופית:", "תשובה: " + result));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התרגיל הזה, חבר את המספר הראשון עם המספר השני  ",
                        "*✦ step 2:  זה אמור להראות ככה : " + b + " +  " + a,
                        "*✦ step 3: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_TWO:
                a += b;
                result = a - b;

                content = String.format("%s קנתה %d %s, אך נתנה %d ל%s. *כמה %s נשאר ל%s?", girlName, a, fruitName, b, boyName, fruitName,girlName);
                solution = String.valueOf(result);

                steps.add(new SolutionStep(1, "זהה את המספרים שעליך להפחית.",
                        "החסר את " + b + " מתוך " + a));
                steps.add(new SolutionStep(2, "בצע את פעולת החיסור.",
                        a + " - " + b + " = " + result));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "כמות ה" + fruitName + " שנותרה אצל " + girlName + " היא " + result));
                steps.add(new SolutionStep(4, "תשובה סופית:", "תשובה: " + result));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התרגיל הזה, חסר את המספר השני מהמספר הראשון",
                        "*✦ step 2:  זה אמור להראות ככה : " + b + " -  " + a,
                        "*✦ step 3: רשום את התשובה הסופית שיצאה לך במספרים"
                );
                break;
            case LEVEL_THREE:
                String storeName = storeNames.get(random.nextInt(storeNames.size()));
                String productName = productNames.get(random.nextInt(productNames.size()));

                result = a * b;

                content = String.format("%s הלך ל%s וקנה %d חבילות של %s. בכל חבילה יש %d %s. *כמה %s יש בסך הכל?",
                        boyName, storeName, a, productName, b, productName, productName);
                solution = String.valueOf(result);

                steps.add(new SolutionStep(1, "זהה את המספרים שעליך להכפיל.",
                        "הכפל את " + a + " ב-" + b));
                steps.add(new SolutionStep(2, "בצע את פעולת הכפל.",
                        a + " ✱ " + b + " = " + result));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "בסך הכל יש " + productName, " -- > " + result));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התוצאה, הכפל את מספר החבילות בכמות הפריטים בכל חבילה.",
                        "*✦ step 2: בצע את פעולת הכפל: " + a + " ✱ " + b,
                        "*✦ step 3: רשום את התוצאה הסופית."
                );
                break;
            case LEVEL_FOUR:
            default:
                String drinkName = drinkNames.get(random.nextInt(drinkNames.size()));

                a  = b * generateRandomNumber(difficulty);
                result = a / b;
                content = String.format("%s קנה %d בקבוקי %s ורוצה לחלק אותם שווה בשווה בין %d חברים. *כמה בקבוקים יקבל כל חבר?",
                        girlName, a, drinkName, b);
                solution = String.valueOf(result);

                steps.add(new SolutionStep(1, "זהה את המספרים שעליך לחלק.",
                        "חלק את " + a + " ב-" + b));
                steps.add(new SolutionStep(2, "בצע את פעולת החילוק.",
                        a + " ➗ " + b + " = " + result));
                steps.add(new SolutionStep(3, "רשום את התוצאה הסופית.",
                        "כל חבר יקבל " + result + " בקבוקי " + drinkName,"התשובה היא : " + result));

                explanation = String.join("\n",
                        "✦ step 1: כדי לחשב את התוצאה, חלק את מספר הבקבוקים במספר החברים.",
                        "*✦ step 2: בצע את פעולת החילוק: " + a + " ➗ " + b,
                        "*✦ step 3: רשום את התוצאה הסופית."
                );
                break;
        }

        return new Question(WORD_PROBLEM, content, difficulty, solution, explanation,steps);
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



}