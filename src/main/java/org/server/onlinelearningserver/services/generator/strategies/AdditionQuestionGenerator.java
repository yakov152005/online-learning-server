package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.RandomUtils.calculateResult;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;

@Service
public class AdditionQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
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
}

