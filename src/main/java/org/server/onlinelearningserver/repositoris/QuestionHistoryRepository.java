package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.QuestionHistory;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory,Long> {

    List<QuestionHistory> findByUser(@Param("user") User user);

    @Query("""
           SELECT COUNT(qh) > 0 FROM QuestionHistory qh
           WHERE qh.user = :user
           AND qh.question.category = :category
           AND qh.question.difficulty = :level
           """)
    boolean wasLevelReached(User user, String category, int level);
}
