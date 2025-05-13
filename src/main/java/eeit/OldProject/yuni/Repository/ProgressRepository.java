package eeit.OldProject.yuni.Repository;

import eeit.OldProject.yuni.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.yuni.Entity.Progress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {

//    @Query("SELECT p FROM Progress p WHERE p.userId.userId = :userId AND p.courseId.courseId = :courseId")
//    List<Progress> findByUserId_UserIdAndCourseId_CourseId(@Param("userId") Long userId, @Param("courseId") Integer courseId);

    @Query("SELECT p FROM Progress p JOIN FETCH p.userId JOIN FETCH p.courseId WHERE p.userId.userId = :userId AND p.courseId.courseId = :courseId")
    List<Progress> findByUserId_UserIdAndCourseId_CourseId(@Param("userId") Long userId, @Param("courseId") Integer courseId);


//    List<Progress> findByUserId_UserIdAndCourseId_CourseId(Long userId, Integer courseId);

    Optional<Progress> findByUserId_UserIdAndChapterId_ChapterId(Long userId, Integer chapterId);

    Integer countByUserId_UserIdAndCourseId_CourseIdAndIsCompletedTrue(Long userId, Integer courseId);
//    Integer countByUserId_UserIdAndChapterId_ChapterIdAndIsCompleted(Long userId, Integer chapterId, Boolean isCompleted);

    Integer countByUserId_UserIdAndCourseId_CourseId(Long userId, Integer courseId);

    Boolean existsByUserId_UserIdAndCourseId_CourseId(Long userId, Integer courseId);

    @Query("SELECT DISTINCT p.courseId FROM Progress p WHERE p.userId.userId = :userId")
    List<Course> findDistinctCoursesByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT p.courseId.courseId FROM Progress p WHERE p.userId.userId = :userId AND p.status = 'completed'")
    List<Integer> findCompletedCoursesByUserId(@Param("userId") Long userId);


    @Query("SELECT p FROM Progress p " + "JOIN FETCH p.userId " + "JOIN FETCH p.courseId " + "JOIN FETCH p.chapterId")
    List<Progress> findAllWithRelations();

}


