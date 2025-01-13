package org.server.onlinelearningserver.dtos;

import lombok.Data;

@Data
public class QuestionDto {
    private long id;
    private String category;
    private String content;
    private int difficulty;
    private String solution;
    private String explanation;


    public QuestionDto(long id, String category, String content, int difficulty, String solution,String explanation) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
        this.explanation = explanation;
    }

    public QuestionDto(long id,String category, String content, int difficulty) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
    }
}
