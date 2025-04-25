package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {
}
