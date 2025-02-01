package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.entitys.Session;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.SessionRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.LoginResponse;
import org.server.onlinelearningserver.responses.UserCoinsResponse;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.server.onlinelearningserver.services.HelpMethods.checkPassword;
import static org.server.onlinelearningserver.utils.ApiEmailProcessor.sendEmail;
import static org.server.onlinelearningserver.utils.Constants.Errors.ERROR_PASSWORD;
import static org.server.onlinelearningserver.utils.Constants.UrlClient.URL_CLIENT_PC;
import static org.server.onlinelearningserver.utils.GeneratorUtils.*;
import static org.server.onlinelearningserver.services.HelpMethods.checkAllFiled;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public BasicResponse addUser(@RequestBody User user) {
        User newUser = userRepository.findById(user.getId()).orElse(null);

        if (newUser == null) {
            String error = checkAllFiled(user, userRepository);
            if (!(error.isEmpty())) {
                return new BasicResponse(false, error);
            }
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);

        return new BasicResponse(true, "Success: user " + user.getUsername() + " created.");
    }

    public LoginResponse loginUser(@RequestBody Map<String, String> loginDetails) {
        Map<String, String> response = new HashMap<>();
        String email = loginDetails.get("email");
        String pass = loginDetails.get("password");

        User user = userRepository.findUserByEmail(email);
        if (user == null){
            return new LoginResponse(false,"This Email is not exist, Please Sign Up.",null);
        }

        if (email != null && pass != null) {
            User requestLogin = userRepository.findUserByEmail(email);
            if (requestLogin != null) {
                String hashedPassword = hashPassword(pass, requestLogin.getSalt());
                if (hashedPassword.equals(requestLogin.getPasswordHash())) {
                    String token = JwtUtils.generateToken(requestLogin.getUsername());
                    Session session = sessionRepository.findByUser(requestLogin);

                    if (session != null) {
                        session.setLastActivity(new Date());
                        sessionRepository.save(session);


                        response.put("token", token);
                        return new LoginResponse(true, "Login successfully (existing session)", response);
                    }else {
                        Session newSession = new Session();
                        newSession.setUser(requestLogin);
                        newSession.setLastActivity(new Date());
                        sessionRepository.save(newSession);

                        response.put("token", token);
                        return new LoginResponse(true, "Login successfully (new session)", response);
                    }

                } else {
                    return new LoginResponse(false, "The password is incorrect.", null);
                }
            }
        }
        return new LoginResponse(false, "Email Or Password incorrect.", null);
    }

    public BasicResponse changePassword(@RequestBody Map<String, String> changePasswordDetails) {
        String username = changePasswordDetails.get("username");
        String currentPassword = changePasswordDetails.get("currentPassword");
        String newPassword = changePasswordDetails.get("newPassword");

        boolean checkNewPassword = checkPassword(newPassword);

        User user = userRepository.findByUsername(username);
        if (user != null && checkNewPassword) {

            String storedSalt = user.getSalt();
            String currentPasswordHash = hashPassword(currentPassword, storedSalt);

            String storedPasswordHash = user.getPasswordHash();
            if (storedPasswordHash.equals(currentPasswordHash)) {

                String newSalt = generateSalt();
                String hashedPassword = hashPassword(newPassword, newSalt);

                user.setSalt(newSalt);
                user.setPasswordHash(hashedPassword);
                userRepository.save(user);

                return new BasicResponse(true, "The password successful Changed");
            } else {
                return new BasicResponse(false, "The current password you entered is incorrect.");
            }
        }
        return new BasicResponse(false, ERROR_PASSWORD);
    }

    public BasicResponse resetPasswordForThisUser(@PathVariable String email, @PathVariable String username) {
        User user = userRepository.findByEmailIgnoreCase(email);
        User user1 = userRepository.findByUsername(username);

        if (user == null ) {
            return new BasicResponse(false, "This email does not exist");
        }

        if (user1 == null ) {
            return new BasicResponse(false, "This username does not exist");
        }

        if (!user.getUsername().equals(user1.getUsername())) {
            return new BasicResponse(false, "Username does not match email, reset password failed.");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setTokenExpiryDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        String resetLink = URL_CLIENT_PC + "/confirm-reset?token=" + resetToken;

        String emailContent = "<div style=\"max-width: 500px; margin: auto; padding: 20px; "
                              + "border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); "
                              + "background: rgba(255, 255, 255, 0.8); font-family: Arial, sans-serif; text-align: center;\">"
                              + "<h5 style=\"color: #333; font-size: 20px;\">Confirm Password Reset</h5>"
                              + "<p style=\"font-size: 14px; color: #555; font-weight: bold;\">Someone requested a password reset for your account.</p>"
                              + "<p style=\"font-size: 14px; color: #555; font-weight: bold;\">If this was not you, ignore this email.</p>"
                              + "<p style=\"font-size: 14px; color: #555; font-weight: bold;\">Otherwise, click the button below to reset your password</p>"
                              + "<a href=\"" + resetLink + "\" style=\"display: inline-block; background: linear-gradient(135deg, #007bff, #0056b3); "
                              + "color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; "
                              + "font-weight: bold; font-size: 16px; box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);\">Reset Password</a>"
                              + "</div>";
        sendEmail(user.getEmail(), "Confirm Password Reset", emailContent);


        return new BasicResponse(true, "An email has been sent with a confirmation link.");
    }


    public BasicResponse confirmPasswordReset(@RequestParam String token) {
        User user = userRepository.findByResetToken(token);

        if (user == null || user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return new BasicResponse(false, "Invalid or expired token.");
        }


        String newPassword = generatorPassword();
        String salt = generateSalt();
        String hashedPassword = hashPassword(newPassword, salt);


        user.setSalt(salt);
        user.setPasswordHash(hashedPassword);
        user.setResetToken(null);
        user.setTokenExpiryDate(null);
        userRepository.save(user);

        String emailContent = "<div style=\"max-width: 500px; margin: auto; padding: 20px; "
                              + "border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); "
                              + "background: rgba(255, 255, 255, 0.8); font-family: Arial, sans-serif; text-align: center;\">"
                              + "<h5 style=\"color: #333; font-size: 20px;\">Your password has been successfully reset</h5>"
                              + "<p style=\"font-size: 14px; color: #555; font-weight: bold;\">Your new password: " + newPassword + "</p>"
                              + "</div>";
        sendEmail(user.getEmail(), "Your New Password", emailContent);

        return new BasicResponse(true, "Your password has been reset. Check your email for the new password.");
    }

    public UserCoinsResponse getCoins(@RequestParam String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return new UserCoinsResponse(false,"This User is not exist.");
        }

        int coinsCredits = user.getCoinsCredits();

        return new UserCoinsResponse(true,"User coins sends.",coinsCredits);
    }


}
