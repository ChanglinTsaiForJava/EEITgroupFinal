package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {
    Optional<CourseRating> findByUserIdAndCourseId(Long userId, Long courseId);
    List<CourseRating> findByCourseId(Long courseId);
}
