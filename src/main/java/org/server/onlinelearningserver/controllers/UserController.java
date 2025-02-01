package org.server.onlinelearningserver.controllers;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.LoginResponse;
import org.server.onlinelearningserver.responses.UserCoinsResponse;
import org.server.onlinelearningserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.server.onlinelearningserver.utils.Constants.UrlClient.URL_SERVER;

@RestController
@RequestMapping(URL_SERVER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public BasicResponse addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/login-user")
    public LoginResponse loginUser(@RequestBody Map<String,String> loginDetails){
        return userService.loginUser(loginDetails);
    }

    @PostMapping("/change-password")
    public BasicResponse changePassword(@RequestBody Map<String, String> changePasswordDetails) {
        return userService.changePassword(changePasswordDetails);
    }

    @GetMapping("/reset-password/{email}&{username}")
    public BasicResponse resetPasswordForThisUser(@PathVariable String email, @PathVariable String username) {
        return userService.resetPasswordForThisUser(email,username);
    }

    @PostMapping("/confirm-reset-password")
    public BasicResponse confirmResetPassword(@RequestParam String token){
        System.out.println(token + " token");
        return userService.confirmPasswordReset(token);
    }

    @GetMapping("/get-user-coins")
    public UserCoinsResponse getCoins(@RequestParam String username){
        return userService.getCoins(username);
    }

}
