package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.entitys.Session;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.SessionRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.LoginResponse;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.server.onlinelearningserver.utils.GeneratorUtils.*;
import static org.server.onlinelearningserver.utils.HelpMethods.checkAllFiled;

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


}
