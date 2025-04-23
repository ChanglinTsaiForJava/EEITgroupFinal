package eeit.OldProject.steve.Service;
import eeit.OldProject.steve.Entity.EmployeeRating;
import eeit.OldProject.steve.Repository.EmployeeRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeRatingServiceImpl implements EmployeeRatingService {

    @Autowired
    private EmployeeRatingRepository ratingRepository;

    @Override
    public List<EmployeeRating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public EmployeeRating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public EmployeeRating createRating(EmployeeRating rating) {
        rating.setArchivedDate(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public EmployeeRating updateRating(Long id, EmployeeRating updatedRating) {
        return ratingRepository.findById(id).map(rating -> {
            rating.setRatings(updatedRating.getRatings());
            rating.setFeedback(updatedRating.getFeedback());
            return ratingRepository.save(rating);
        }).orElse(null);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
