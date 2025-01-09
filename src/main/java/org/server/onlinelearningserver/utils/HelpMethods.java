package org.server.onlinelearningserver.utils;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.UserRepository;

import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.Errors.*;
import static org.server.onlinelearningserver.utils.Constants.HelpMethodConstants.*;

public class HelpMethods {


    public static boolean checkIfUserNameValid(String username, List<String> users){
        String currentUser = users.stream().filter(current ->  current.equals(username)).findFirst().orElse(null);
        if (currentUser!= null){
            System.out.println("Current " + currentUser + " user " + username);
            return currentUser.equals(username);
        }
        return false;
    }

    public static boolean checkPassword(String password){
        boolean oneLatterOrMore = oneCharOrMore(password,LETTERS);
        boolean oneSpecialCharOrMore = oneCharOrMore(password,SPECIAL_CHAR);
        return password.length() >= LENGTH_PASSWORD && oneLatterOrMore && oneSpecialCharOrMore;
    }

    public static boolean oneCharOrMore(String password,String forCheck){
        String toLowerCase = forCheck.toLowerCase();
        for (int i = 0; i < password.length() ; i++) {
            char chPassword = password.charAt(i);
            for (int j = 0; j < forCheck.length() ; j++) {
                char chLetters = forCheck.charAt(j);
                char chLettersLower = toLowerCase.charAt(j);
                if (chPassword == chLetters || chPassword == chLettersLower){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean confirmPasswordEquals(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public static boolean checkEmailValid(String email) {
        String[] temp = email.split("@");
        if (temp.length != 2) {
            return false;
        }
        String domain = temp[1];
        for (String validDomain : EMAILS_CONTAINS) {
            if (domain.equalsIgnoreCase(validDomain)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfEmailExist(String email, UserRepository userRepository){
        for (User repUser : userRepository.findAll()){
            if (email.equalsIgnoreCase(repUser.getEmail())){
                return true;
            }
        }
        return false;
    }

    public static String checkAllFiled(User user,UserRepository userRepository){
        List<String> users = userRepository.findAll().stream().map(User::getUsername).toList();

        if (checkIfUserNameValid(user.getUsername(),users)){
            return ERROR_USER;
        }
        if (!checkPassword(user.getPassword())){
            return ERROR_PASSWORD;
        }
        if (!confirmPasswordEquals(user.getPassword(),user.getPasswordConfirm())){
            return ERROR_PASSWORD_CONFIRM;
        }
        if (!checkEmailValid(user.getEmail())) {
            return ERROR_EMAIL_VALID;
        }
        if (checkIfEmailExist(user.getEmail(),userRepository)) {
            return ERROR_EMAIL_EXIST;
        }

        return NO_ERROR;
    }

}
