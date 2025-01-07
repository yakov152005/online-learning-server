package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
