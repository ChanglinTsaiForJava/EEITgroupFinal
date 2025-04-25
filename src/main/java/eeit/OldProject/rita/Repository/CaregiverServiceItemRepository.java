package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.CaregiverServiceItem;
import eeit.OldProject.rita.Entity.CaregiverServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverServiceItemRepository extends JpaRepository<CaregiverServiceItem, Long> {
}
