package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.Session;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByUser(User user);
}
