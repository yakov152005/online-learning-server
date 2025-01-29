package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SubmitResponse extends BasicResponse{
    private boolean levelUp;
    private boolean levelDown;
    private String solution;
    private Map<String, Integer> successStreaksByCategory;


    public SubmitResponse(boolean success, String error, boolean levelUp) {
        super(success, error);
        this.levelUp = levelUp;
    }

    public SubmitResponse(boolean success, String error, boolean levelUp,boolean levelDown,String solution,Map<String, Integer> successStreaksByCategory) {
        super(success, error);
        this.levelUp = levelUp;
        this.levelDown = levelDown;
        this.solution = solution;
        this.successStreaksByCategory = successStreaksByCategory;
    }
}
