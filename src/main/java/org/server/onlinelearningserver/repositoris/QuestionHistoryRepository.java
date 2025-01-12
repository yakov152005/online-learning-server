package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory,Long> {

}
