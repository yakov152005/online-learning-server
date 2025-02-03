package org.server.onlinelearningserver.entitys;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;

@Entity
@Table(name = "WEEKLY_STATS")
@Data
public class WeeklyStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = true)
    private double currentTotalSuccessRate = 0.0;

    @Column(nullable = false, updatable = true)
    private double previousTotalSuccessRate = 0.0;

    @Column(nullable = false, updatable = true)
    private Date recordedAt;

    @Column(nullable = true, updatable = true)
    private Date previousWeekDate;
}