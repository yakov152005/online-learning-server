package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;
import org.server.onlinelearningserver.dtos.QuestionDto;

@Getter
@Setter
public class QuestionResponse extends BasicResponse{
    private QuestionDto questionDto;
    private int successStreak;
    private int currentLevel;

    public QuestionResponse(boolean success, String error, QuestionDto questionDto,int successStreak,int currentLevel) {
        super(success, error);
        this.questionDto = questionDto;
        this.successStreak = successStreak;
        this.currentLevel = currentLevel;
    }
    public QuestionResponse(boolean success, String error, QuestionDto questionDto) {
        super(success, error);
        this.questionDto = questionDto;
    }

}
