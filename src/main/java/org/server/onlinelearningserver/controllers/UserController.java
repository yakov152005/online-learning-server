package org.server.onlinelearningserver.controllers;

import org.server.onlinelearningserver.dtos.UserDto;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.LoginResponse;
import org.server.onlinelearningserver.responses.UserCoinsResponse;
import org.server.onlinelearningserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
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
        return userService.confirmPasswordReset(token);
    }

    @GetMapping("/get-user-coins")
    public UserCoinsResponse getCoins(@RequestParam String username){
        return userService.getCoins(username);
    }


    @PostMapping("/update-coins/{username}&={coinsCredits}")
    public BasicResponse updateCoins(@PathVariable String username, @PathVariable int coinsCredits){
        return userService.updateCoins(username,coinsCredits);
    }

    @GetMapping("/get-all-users")
    public List<UserDto> getAllUsers(@RequestHeader("Authorization") String token, @RequestParam String username) {
        return userService.getAllUsers(token,username);
    }

    @PostMapping("/send-message")
    public BasicResponse sendMessageToUser(@RequestParam String username, @RequestParam String message) {
        return userService.sendMessageToUser(username,message);
    }

    @PostMapping("/send-mail")
    public BasicResponse sendMailToUser(@RequestParam String email, @RequestParam String message){
        return userService.sendMailToUser(email,message);
    }
}
