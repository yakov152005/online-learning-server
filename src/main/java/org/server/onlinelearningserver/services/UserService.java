package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static org.server.onlinelearningserver.utils.GeneratorUtils.generateSalt;
import static org.server.onlinelearningserver.utils.GeneratorUtils.hashPassword;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private BasicResponse addUser(@RequestBody User user) {
        User newUser = userRepository.findById(user.getId()).orElse(null);

        if (newUser != null) {
            String currentUsername = user.getUsername().toLowerCase();
            String newUsername = newUser.getUsername().toLowerCase();
            if (currentUsername.equals(newUsername)) {
                return new BasicResponse(false, "Error: this username is exist.");
            }
        }
        // להוסיף כאן את הבדיקות

        String salt = generateSalt();
        String hashedPassword = hashPassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);

        return new BasicResponse(true, "Success: user " + user.getUsername() + " created.");
    }

}
