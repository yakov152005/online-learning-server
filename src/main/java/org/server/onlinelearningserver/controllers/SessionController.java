package org.server.onlinelearningserver.controllers;

import org.server.onlinelearningserver.responses.TokenResponse;
import org.server.onlinelearningserver.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.server.onlinelearningserver.utils.Constants.UrlClient.URL_SERVER;

@RestController
@RequestMapping(URL_SERVER)
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @PostMapping("/validateToken")
    public ResponseEntity<TokenResponse> validateToken(@RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Token missing");
            return ResponseEntity.badRequest().body(new TokenResponse(false, "Token is missing", false,null));
        }

        String cleanToken = token.replace("Bearer ", "");

        TokenResponse response = sessionService.validateToken(cleanToken);
        return ResponseEntity.ok(response);
    }






}