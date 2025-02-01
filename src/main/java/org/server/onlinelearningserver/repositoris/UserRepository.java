package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u NOT IN " +
           "(SELECT s.user FROM Session s WHERE s.lastActivity >= :lastWeek)")
    List<User> findUsersNotLoggedInLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT u FROM User u WHERE u.resetToken = :token")
    User findByResetToken(@Param("token") String token);

}
