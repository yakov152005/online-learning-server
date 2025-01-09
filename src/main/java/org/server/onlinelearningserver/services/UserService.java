package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.entitys.Login;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.LoginRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.LoginResponse;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

import static org.server.onlinelearningserver.utils.GeneratorUtils.*;
import static org.server.onlinelearningserver.utils.HelpMethods.checkAllFiled;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    @Autowired
    public UserService(UserRepository userRepository,LoginRepository loginRepository) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
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
        String email = loginDetails.get("email");
        String pass = loginDetails.get("password");

        if (email != null && pass != null) {
            User requestLogin = userRepository.findUserByEmail(email);
            if (requestLogin != null) {
                String hashedPassword = hashPassword(pass, requestLogin.getSalt());
                if (hashedPassword.equals(requestLogin.getPasswordHash())) {
                    String token = JwtUtils.generateToken(requestLogin.getUsername());
                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    Login login = new Login();
                    login.setUser(requestLogin);
                    loginRepository.save(login);
                    return new LoginResponse(true, "Login successfully", response);
                }else {
                    return new LoginResponse(false,"The password is incorrect.",null);
                }
            }
        }
        return new LoginResponse(false, "Email Or Password incorrect.", null);
    }

}
