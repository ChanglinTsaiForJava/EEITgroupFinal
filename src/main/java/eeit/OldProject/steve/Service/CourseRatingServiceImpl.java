package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.CourseRating;
import eeit.OldProject.steve.Repository.CourseRatingRepository;
import eeit.OldProject.steve.Service.CourseRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseRatingServiceImpl implements CourseRatingService {

    @Autowired
    private CourseRatingRepository courseRatingRepository;

    @Override
    public CourseRating addRating(CourseRating rating, Long userId) {
        // 防止重複評價
        Optional<CourseRating> existing = courseRatingRepository.findByUserIdAndCourseId(userId, rating.getCourseId());
        if (existing.isPresent()) {
            throw new IllegalStateException("已對此課程評價過，無法重複評價");
        }

        rating.setUserId(userId);
        rating.setArchivedDate(LocalDateTime.now());
        return courseRatingRepository.save(rating);
    }


    @Override
    public List<CourseRating> getAllRatings() {
        return courseRatingRepository.findAll();
    }

    @Override
    public Optional<CourseRating> getRatingById(Long id) {
        return courseRatingRepository.findById(id);
    }

    @Override
    public List<CourseRating> getRatingsByCourseId(Long courseId) {
        return courseRatingRepository.findByCourseId(courseId);
    }

    @Override
    public CourseRating updateRating(CourseRating existing, CourseRating updated) {
        existing.setRatings(updated.getRatings());
        existing.setFeedback(updated.getFeedback());
        return courseRatingRepository.save(existing);
    }

    @Override
    public void deleteRating(CourseRating rating) {
        courseRatingRepository.delete(rating);
    }
}

