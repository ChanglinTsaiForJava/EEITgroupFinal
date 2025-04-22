package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.EmployeeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRatingRepository extends JpaRepository<EmployeeRating, Long> {
}
