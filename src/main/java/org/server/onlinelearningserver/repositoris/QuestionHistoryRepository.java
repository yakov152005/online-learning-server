package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.QuestionHistory;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory,Long> {

    List<QuestionHistory> findByUser(@Param("user") User user);

}
