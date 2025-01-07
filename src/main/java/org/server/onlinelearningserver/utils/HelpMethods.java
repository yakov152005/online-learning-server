package org.server.onlinelearningserver.utils;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.UserRepository;

import java.util.List;

import static org.server.onlinelearningserver.utils.Constants.HelpMethodConstants.EMAILS_CONTAINS;

public class HelpMethods {

    public static boolean checkIfUserNameValid(String username, List<String> user){
        String currentUser = user.stream().filter(current ->  current.equals(username)).findFirst().orElse(null);
        if (currentUser!= null){
            System.out.println("Current " + currentUser + " user " + username);
            if (currentUser.equals(username)){
                return true;
            }
        }
        return false;
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
}
