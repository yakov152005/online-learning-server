package org.server.onlinelearningserver.dtos;

import lombok.Data;

@Data
public class QuestionDto {
    private long id;
    private String category;
    private String content;
    private int difficulty;
    private String solution;

    public QuestionDto(long id, String category, String content, int difficulty, String solution) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
    }
}
