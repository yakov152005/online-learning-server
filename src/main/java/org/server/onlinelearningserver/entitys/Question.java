package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QUESTION")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "progress_id", nullable = false)
    private Progress progress;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int difficulty;

    @Column(nullable = false)
    private String solution;

    @Transient
    private String explanation;

    @Column(nullable = false)
    private boolean answered = false;

    @Transient
    private List<SolutionStep> steps;

    public Question(String category, String content, int difficulty, String solution, String explanation) {
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
        this.explanation = explanation;
    }

    public Question(String category, String content, int difficulty, String solution, String explanation,List<SolutionStep> steps) {
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
        this.explanation = explanation;
        this.steps = steps;
    }

    public Question() {

    }


}
