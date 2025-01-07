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

    public class UrlClient{
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

    }
}
