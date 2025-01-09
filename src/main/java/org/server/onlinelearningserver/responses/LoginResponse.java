package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class LoginResponse extends BasicResponse{
    private Map<String, String> verificationLogin;

    public LoginResponse(boolean success, String error, Map<String, String> verificationLogin) {
        super(success, error);
        this.verificationLogin = verificationLogin;
    }

}
