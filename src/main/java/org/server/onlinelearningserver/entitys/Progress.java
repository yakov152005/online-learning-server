package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "PROGRESS")
@Data
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne()
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "category_success_streak", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "success_streak")
    private Map<String, Integer> categorySuccessStreak = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "weak_points", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "error_count")
    private Map<String, Integer> weakPoints = new HashMap<>();;

    @ElementCollection
    @CollectionTable(name = "category_progress", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "current_level")
    private Map<String, Integer> categoryProgress = new HashMap<>();;

    @Column(nullable = true)
    private String activeCategory;


}
