/*
package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "TOPIC")
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Subtopic> subtopics;

}

 */