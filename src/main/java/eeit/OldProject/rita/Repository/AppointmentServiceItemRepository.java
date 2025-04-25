package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentServiceItemRepository extends JpaRepository<AppointmentServiceItem, Long> {
}
