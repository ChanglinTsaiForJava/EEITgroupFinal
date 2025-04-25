package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceItem, Long> {
}
