package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.Physical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalRepository extends JpaRepository<Physical, Long> {
}
