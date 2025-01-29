package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;
import org.server.onlinelearningserver.dtos.QuestionDto;

@Getter
@Setter
public class QuestionResponse extends BasicResponse{
    private QuestionDto questionDto;
    private int successStreak;

    public QuestionResponse(boolean success, String error, QuestionDto questionDto,int successStreak) {
        super(success, error);
        this.questionDto = questionDto;
        this.successStreak = successStreak;
    }
    public QuestionResponse(boolean success, String error, QuestionDto questionDto) {
        super(success, error);
        this.questionDto = questionDto;
    }

}
