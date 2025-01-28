package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitResponse extends BasicResponse{
    private boolean levelUp;
    private boolean levelDown;
    private String solution;


    public SubmitResponse(boolean success, String error, boolean levelUp) {
        super(success, error);
        this.levelUp = levelUp;
    }

    public SubmitResponse(boolean success, String error, boolean levelUp,boolean levelDown,String solution) {
        super(success, error);
        this.levelUp = levelUp;
        this.levelDown = levelDown;
        this.solution = solution;
    }
}
