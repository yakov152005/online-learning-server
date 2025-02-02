package org.server.onlinelearningserver.entitys;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolutionStep {
    private int stepNumber;
    private String description;
    private String equation1;
    private String equation2;

    public SolutionStep(int stepNumber, String description, String equation1) {
        this.stepNumber = stepNumber;
        this.description = description;
        this.equation1 = equation1;
    }

    public SolutionStep(int stepNumber, String description, String equation, String equation2) {
        this.stepNumber = stepNumber;
        this.description = description;
        this.equation1 = equation;
        this.equation2 = equation2;
    }
}

