package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitResponse extends BasicResponse{
    private boolean levelUp;
    private String solution;


    public SubmitResponse(boolean success, String error, boolean levelUp) {
        super(success, error);
        this.levelUp = levelUp;
    }

    public SubmitResponse(boolean success, String error, boolean levelUp,String solution) {
        super(success, error);
        this.levelUp = levelUp;
        this.solution = solution;
    }
}
