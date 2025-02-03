package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.Session;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.entitys.WeeklyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeeklyStatsRepository extends JpaRepository<WeeklyStats, Long> {
    @Query("SELECT ws FROM WeeklyStats ws WHERE ws.user = :user ORDER BY ws.recordedAt DESC")
    List<WeeklyStats> findLatestByUser(@Param("user") User user);

    @Query("SELECT ws FROM WeeklyStats ws WHERE ws.user = :user AND ws.recordedAt >= :weekStart ORDER BY ws.recordedAt DESC")
    WeeklyStats findStatsForCurrentWeek(@Param("user") User user, @Param("weekStart") Date weekStart);

    WeeklyStats findByUser(User user);
}
