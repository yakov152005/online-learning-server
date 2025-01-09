package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
}
