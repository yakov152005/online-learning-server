package org.server.onlinelearningserver.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class Constants {

    public static Dotenv dotenv = Dotenv.load();

    public static class DataBase{
        public static final String DB_HOST = dotenv.get("DB_HOST");
        public static final String DB_USER = dotenv.get("DB_USER");
        public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
        public static final String DB_NAME = dotenv.get("DB_NAME");
    }

    public static class Mail{
        public static final String SENDER_EMAIL = dotenv.get("SENDER_EMAIL");
        public static final String SENDER_PASSWORD = dotenv.get("SENDER_PASSWORD");
        public static final String PERSONAL = "Online Learning";
        public static final String TITLE = "Hey From Online Learning";
        public static final String CONTENT = "לא התחברת המון זמן, בוא לבקר אותנו :)";
    }

    public  static class UrlClient{
        public static final String URL_SERVER = "/online-learning";
        public static final String URL_CLIENT_PC = dotenv.get("URL_CLIENT_PC");
        public static final String URL_CLIENT_LAPTOP = dotenv.get("URL_CLIENT_LAPTOP");

    }

    public static class HelpMethodConstants{
        public static final String SPECIAL_CHAR = "!@#$%^&*()-+=_";
        public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String[] EMAILS_CONTAINS = {"walla.co.il", "walla.com", "gmail.com", "gmail.co.il", "edu.aac.ac.il"};
        public static final int LENGTH_PASSWORD = 8;
    }

    public static class Errors{
        public static final String NO_ERROR = "";
        public static final String ERROR_USER = "Failed: This username is exist, please choose a another name.";
        public static final String ERROR_PASSWORD = "Failed: The password must be 8 characters long," +
                                             " contain at least one special character -> " + HelpMethodConstants.SPECIAL_CHAR +
                                             ", and at least one letter.";
        public static final String ERROR_PASSWORD_CONFIRM = "Failed: The password confirm  you entered does not match the original password.";
        public static final String ERROR_EMAIL_VALID = "Failed: The email must be in the style 'example@yourmail.com OR .co.il' ";
        public static final String ERROR_EMAIL_EXIST = "Failed: This email is exist, please choose another email.";
    }

    public static class Question{
        public static final int LEVEL_ONE = 1;
        public static final int LEVEL_TWO = 2;
        public static final int LEVEL_THREE = 3;
        public static final int LEVEL_FOUR = 4;
        public static final int LEVEL_FIVE = 5;
        public static final int LEVEL_SIX = 6;

        public static final int FOR_LEVEL_UP = 10;
        public static final int USER_WAS_AT_THE_NEXT_LEVEL = 5;

        public static final int RESET_AFTER_LEVEL_UP = 0;
        public static final int RESET_AFTER_ERROR_ANSWER = 0;

        public static final int ERROR_HIGH_THEN_FIVE = 5;

        public static final int LEVEL_HIGH_THEN_ONE = 1;

        public static final String quadraticForInvoiceFormula = "n₁,₂  = (-b ± √(b² - 4ac)) / 2a";
        public static final String anFormula = "an = a1 + (n -1) ✱ d";
        public static final String snFormula = "Sn = n[2a1 + d(n-1)] / 2";
        public static final String quadraticFormula = "x₁,₂ = (-b ± √(b² - 4ac)) / 2a";
        public static final String equationLineFormula = "y-y1=m(x-x1)";
        public static final String findMFormula = "m = ((y₂ - y₁) / (x₂ - x₁))";
        public static final String mathFormula = "y=mx+b";
        public static final String deltaFormula = "Δ = b² - 4ac";
        public static final String afterDerivativeFormula = "f'(x)=ax+b";
        public static final String afterDerivativeFormula2 = "f'(x)=ax²+bx";
        public static final String roleMultiDerivative = "(fg)' = f' g + fg'";
        public static final String afterDerivativeAXFormula = "n ✱ ax^(n-1)";
        public static final String chainRule = "d/dx f(g(x)) = f'(g(x)) ✱ g'(x)";
        public static final String dishRule = "d/dx (f/g) = (f'g - fg') / g²";



        public static final String ADD = "addition";
        public static final String SUB = "subtraction";
        public static final String MULTI = "multiplication";
        public static final String DIV = "division";
        public static final String WORD_PROBLEM = "wordProblem";
        public static final String INVOICE_SERIES =  "invoiceSeries";
        public static final String QUADRATIC_EQUATION = "quadraticEquation";
        public static final String EQUATION_LINE = "equationOfTheLine";
        public static final String DERIVATIVE = "derivative";

        public static final List<String> hebrewBoysNames = List.of("אורי", "יובל", "איתי", "נועם", "תומר");
        public static final List<String> hebrewGirlsNames = List.of("עדי", "נועה", "ליאן", "שילת", "קארין");
        public static final List<String> items1Names = List.of( "על המדף","בתיק", "בקופסה");
        public static final List<String> items2Names = List.of( "ספרים","מחברות","עפרונות");
        public static final List<String> fruitNames = List.of("אבטיחים", "בננות", "קלמנטינות", "תפוחים","תפוזים");
        public static final List<String> drinkNames = List.of("מים", "מיץ תפוזים", "תה", "קפה", "חלב", "קולה", "לימונדה", "שוקו");
        public static final List<String> productNames = List.of("לחם", "חלב", "חמאה", "שוקולד", "פסטה", "גבינה", "דבש", "קורנפלקס", "אורז", "מלפפונים");
        public static final List<String> storeNames = List.of("סופרמרקט", "מכולת", "שוק", "קונדיטוריה", "מאפייה", "חנות טבע", "קיוסק", "מרכז קניות");

        public static final char[] OPERATORS = {'+','-','✱','/' };
    }
}
