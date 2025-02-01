package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SubmitResponse extends BasicResponse{
    private boolean levelUp;
    private boolean levelDown;
    private String solution;
    private Map<String, Integer> successStreaksByCategory;
    private int coins;
    private Map<String, Integer> currentLevelByCategory;


    public SubmitResponse(boolean success, String error) {
        super(success, error);
    }

    public SubmitResponse(boolean success, String error, boolean levelUp,boolean levelDown,String solution,Map<String, Integer> successStreaksByCategory, int coins,Map<String, Integer> currentLevelByCategory) {
        super(success, error);
        this.levelUp = levelUp;
        this.levelDown = levelDown;
        this.solution = solution;
        this.successStreaksByCategory = successStreaksByCategory;
        this.coins = coins;
        this.currentLevelByCategory = currentLevelByCategory;
    }
}
