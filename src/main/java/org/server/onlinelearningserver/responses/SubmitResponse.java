package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitResponse extends BasicResponse{
    private boolean levelUp;


    public SubmitResponse(boolean success, String error, boolean levelUp) {
        super(success, error);
        this.levelUp = levelUp;
    }
}
