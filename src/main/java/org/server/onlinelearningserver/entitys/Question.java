package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "QUESTION")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int difficulty;

    @Column(nullable = false)
    private String solution;

    private String explanation;

    /*
    @ManyToOne
    @JoinColumn(name = "subtopic_id", nullable = false)
    private Subtopic subtopic;
    */

    public Question(String category, String content, int difficulty, String solution, String explanation) {
        this.category = category;
        this.content = content;
        this.difficulty = difficulty;
        this.solution = solution;
        this.explanation = explanation;
    }

    public Question() {

    }


}
