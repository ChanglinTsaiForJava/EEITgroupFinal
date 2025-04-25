package eeit.OldProject.steve.Service;



import eeit.OldProject.steve.Entity.EmployeeRating;
import java.util.List;
import java.util.Optional;

public interface EmployeeRatingService {
    EmployeeRating addRating(EmployeeRating rating, Long userId);
    List<EmployeeRating> getAllRatings();
    Optional<EmployeeRating> getRatingById(Long id);
    EmployeeRating updateRating(EmployeeRating existing, EmployeeRating updated);
    void deleteRating(EmployeeRating rating);
    List<EmployeeRating> getRatingsByCaregiverId(Long caregiverId);
}

