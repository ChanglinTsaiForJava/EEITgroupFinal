package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.Favorite_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Favorite_courseRepository extends JpaRepository<Favorite_course, Long> {
    // 根據使用者和課程ID查詢收藏
    Optional<Favorite_course> findByUserIdAndCourseId(Long userId, Long courseId);

    // 根據使用者ID查詢所有收藏課程
    List<Favorite_course> findByUserId(Long userId);
}
