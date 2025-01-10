package org.server.onlinelearningserver.services;
import org.server.onlinelearningserver.repositoris.SessionRepository;
import org.server.onlinelearningserver.responses.TokenResponse;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public TokenResponse validateToken(String token) {
        boolean isValid = JwtUtils.isTokenValid(token);
        return new TokenResponse(isValid, isValid ? "Token is valid" : "Token is invalid", isValid);
    }

}