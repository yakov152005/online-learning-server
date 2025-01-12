/*
package org.server.onlinelearningserver.entitys;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "SUBTOPIC")
@Data
public class Subtopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int difficultyLevel;
}

 */