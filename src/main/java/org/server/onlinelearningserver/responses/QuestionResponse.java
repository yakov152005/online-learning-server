package org.server.onlinelearningserver.responses;

import org.server.onlinelearningserver.dtos.QuestionDto;

public class QuestionResponse extends BasicResponse{
    private QuestionDto questionDto;

    public QuestionResponse(boolean success, String error, QuestionDto questionDto) {
        super(success, error);
        this.questionDto = questionDto;
    }

    public QuestionDto getQuestionDto() {
        return questionDto;
    }

    public void setQuestionDto(QuestionDto questionDto) {
        this.questionDto = questionDto;
    }
}
