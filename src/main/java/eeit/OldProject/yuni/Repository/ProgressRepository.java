package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.yuni.Entity.Progress;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    List<Progress> findByUserId_UserIdAndCourseId_CourseId(Long userId, Integer courseId);

    Optional<Progress> findByUserId_UserIdAndChapterId_ChapterId(Long userId, Integer chapterId);

    Integer countByUserId_UserIdAndCourseId_CourseIdAndIsCompletedTrue(Long userId, Integer courseId);
//    Integer countByUserId_UserIdAndChapterId_ChapterIdAndIsCompleted(Long userId, Integer chapterId, Boolean isCompleted);

    Integer countByUserId_UserIdAndCourseId_CourseId(Long userId, Integer courseId);
}


