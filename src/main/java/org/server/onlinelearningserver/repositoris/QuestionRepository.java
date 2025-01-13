package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.dtos.QuestionDto;
import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {


    @Query("SELECT q FROM Question q WHERE q.id = :questionId")
    Question findById(@Param("questionId") String questionId);


    List<Question> findByProgress_UserAndAnsweredFalse(User user);

}
