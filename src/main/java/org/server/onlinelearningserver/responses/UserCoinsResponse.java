package org.server.onlinelearningserver.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCoinsResponse extends BasicResponse{
    private int coinsCredits;

    public UserCoinsResponse(boolean success, String error, int coinsCredits) {
        super(success, error);
        this.coinsCredits = coinsCredits;
    }

    public UserCoinsResponse(boolean success, String error) {
        super(success, error);
    }
}
