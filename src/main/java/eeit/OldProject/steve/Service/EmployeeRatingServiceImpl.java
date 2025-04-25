package eeit.OldProject.steve.Service;


import eeit.OldProject.steve.Entity.EmployeeRating;
import eeit.OldProject.steve.Repository.EmployeeRatingRepository;
import eeit.OldProject.steve.Service.EmployeeRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRatingServiceImpl implements EmployeeRatingService {

    @Autowired
    private EmployeeRatingRepository employeeRatingRepository;

    @Override
    public EmployeeRating addRating(EmployeeRating rating, Long userId) {
        rating.setUserId(userId);
        rating.setArchivedDate(LocalDateTime.now());
        return employeeRatingRepository.save(rating);
    }

    @Override
    public List<EmployeeRating> getAllRatings() {
        return employeeRatingRepository.findAll();
    }

    @Override
    public Optional<EmployeeRating> getRatingById(Long id) {
        return employeeRatingRepository.findById(id);
    }

    @Override
    public EmployeeRating updateRating(EmployeeRating existing, EmployeeRating updated) {
        existing.setRatings(updated.getRatings());
        existing.setFeedback(updated.getFeedback());
        return employeeRatingRepository.save(existing);
    }

    @Override
    public void deleteRating(EmployeeRating rating) {
        employeeRatingRepository.delete(rating);
    }

    @Override
    public List<EmployeeRating> getRatingsByCaregiverId(Long caregiverId) {
        return employeeRatingRepository.findByCaregiverId(caregiverId);
    }
}
