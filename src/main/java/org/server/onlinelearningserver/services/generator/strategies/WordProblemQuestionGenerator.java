package org.server.onlinelearningserver.services.generator.strategies;

import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.SolutionStep;
import org.server.onlinelearningserver.services.generator.QuestionGeneratorStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Question.*;
import static org.server.onlinelearningserver.utils.RandomUtils.generateRandomNumber;
import static org.server.onlinelearningserver.utils.RandomUtils.random;

@Service
public class WordProblemQuestionGenerator implements QuestionGeneratorStrategy {
    @Override
    public Question generateQuestion(int difficulty) {
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

}
