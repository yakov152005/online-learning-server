package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Transient
    private String password;
    @Transient
    private String passwordConfirm;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String salt;
    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Progress progress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionHistory> questionHistories;
}
