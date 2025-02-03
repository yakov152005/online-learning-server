package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.Constants.Question.INVOICE_SERIES;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumberForN;

@Service
public class InvoiceSeriesQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
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
}
