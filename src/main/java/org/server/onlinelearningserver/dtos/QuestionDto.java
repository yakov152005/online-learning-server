package org.server.onlinelearningserver.dtos;

import lombok.Data;
import org.server.onlinelearningserver.entitys.SolutionStep;

import java.util.List;

@Data
public class QuestionDto {
    private long id;
    private String category;
    private String content;
    private int difficulty;
    private String solution;
    private String explanation;
    private List<SolutionStep> steps;


    public QuestionDto(long id, String category, String content, int difficulty, String solution,String explanation,List<SolutionStep> steps) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
        this.explanation = explanation;
        this.steps = steps;
    }

    public QuestionDto(long id,String category, String content, int difficulty) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
    }
}
