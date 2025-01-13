package org.server.onlinelearningserver.repositoris;

import org.server.onlinelearningserver.dtos.CategoryProgressDto;
import org.server.onlinelearningserver.dtos.CategorySuccessStreakDto;
import org.server.onlinelearningserver.dtos.WeakPointDto;
import org.server.onlinelearningserver.entitys.Progress;
import org.server.onlinelearningserver.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("SELECT p FROM Progress p WHERE p.user = :user")
    Progress findByUser(@Param("user") User user);

    @Query("SELECT new org.server.onlinelearningserver.dtos.CategoryProgressDto(KEY(e), VALUE(e)) " +
           "FROM Progress p JOIN p.categoryProgress e WHERE p.user = :user")
    List<CategoryProgressDto> findCategoryProgressByUser(@Param("user") User user);


    @Query("SELECT new org.server.onlinelearningserver.dtos.WeakPointDto(KEY(e), VALUE(e)) " +
           "FROM Progress p JOIN p.weakPoints e WHERE p.user = :user")
    List<WeakPointDto> findWeakPointsByUser(@Param("user") User user);


    @Query("SELECT new org.server.onlinelearningserver.dtos.CategorySuccessStreakDto(KEY(e), VALUE(e)) " +
           "FROM Progress p JOIN p.categorySuccessStreak e WHERE p.user = :user")
    List<CategorySuccessStreakDto> findSuccessStreakByUser(@Param("user") User user);



    List<Progress> findAllByUser(@Param("user") User user);
}