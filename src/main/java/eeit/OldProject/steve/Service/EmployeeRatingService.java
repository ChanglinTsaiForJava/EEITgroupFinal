package eeit.OldProject.steve.Service;


import eeit.OldProject.steve.Entity.EmployeeRating;

import java.util.List;

public interface EmployeeRatingService {
    List<EmployeeRating> getAllRatings();
    EmployeeRating getRatingById(Long id);
    EmployeeRating createRating(EmployeeRating rating);
    EmployeeRating updateRating(Long id, EmployeeRating updatedRating);
    void deleteRating(Long id);
}

