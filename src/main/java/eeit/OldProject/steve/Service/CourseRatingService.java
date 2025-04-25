package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.CourseRating;

import java.util.List;
import java.util.Optional;

public interface CourseRatingService {
    CourseRating addRating(CourseRating rating, Long userId);
    List<CourseRating> getAllRatings();
    Optional<CourseRating> getRatingById(Long id);
    CourseRating updateRating(CourseRating existing, CourseRating updated);
    void deleteRating(CourseRating rating);
    List<CourseRating> getRatingsByCourseId(Long courseId);
}
