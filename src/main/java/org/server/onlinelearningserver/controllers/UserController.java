package org.server.onlinelearningserver.controllers;

import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.server.onlinelearningserver.utils.Constants.UrlClient.URL_SERVER;

@RestController
@RequestMapping(URL_SERVER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/hello-from-server")
    public BasicResponse helloFromServer(){
        return new BasicResponse(true,"Hello from server!");
    }




}
