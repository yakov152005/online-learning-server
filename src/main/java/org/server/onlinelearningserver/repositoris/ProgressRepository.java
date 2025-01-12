package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.Progress;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("SELECT p FROM Progress p WHERE p.user = :user")
    Progress findByUser(@Param("user") User user);

}
