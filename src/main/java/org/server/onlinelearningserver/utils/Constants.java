package org.server.onlinelearningserver.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Constants {

    public static Dotenv dotenv = Dotenv.load();

    public class DataBase{
        public static final String DB_HOST = dotenv.get("DB_HOST");
        public static final String DB_USER = dotenv.get("DB_USER");
        public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
        public static final String DB_NAME = dotenv.get("DB_NAME");
    }

    public class Mail{
        public static final String SENDER_EMAIL = dotenv.get("SENDER_EMAIL");
        public static final String SENDER_PASSWORD = dotenv.get("SENDER_PASSWORD");
        public static final String PERSONAL = "Online Learning";
        public static final String TITLE = "Hey From Online Learning";
        public static final String CONTENT = "לא התחברת המון זמן, בוא לבקר אותנו :)";
    }

    public  class UrlClient{
        public static final String URL_SERVER = "/online-learning";
        public static final String URL_CLIENT_PC = dotenv.get("URL_CLIENT_PC");
        public static final String URL_CLIENT_LAPTOP = dotenv.get("URL_CLIENT_LAPTOP");

    }

    public class HelpMethodConstants{
        public static final String SPECIAL_CHAR = "!@#$%^&*()-+=_";
        public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String[] EMAILS_CONTAINS = {"walla.co.il", "walla.com", "gmail.com", "gmail.co.il", "edu.aac.ac.il"};
        public static final int LENGTH_PASSWORD = 8;
    }

    public class Errors{
        public static final String NO_ERROR = "";
        public static final String ERROR_USER = "Failed: This username is exist, please choose a another name.";
        public static final String ERROR_PASSWORD = "Failed: The password must be 8 characters long," +
                                             " contain at least one special character -> " + HelpMethodConstants.SPECIAL_CHAR +
                                             ", and at least one letter.";
        public static final String ERROR_PASSWORD_CONFIRM = "Failed: The password confirm  you entered does not match the original password.";
        public static final String ERROR_EMAIL_VALID = "Failed: The email must be in the style 'example@yourmail.com OR .co.il' ";
        public static final String ERROR_EMAIL_EXIST = "Failed: This email is exist, please choose another email.";
    }
}
