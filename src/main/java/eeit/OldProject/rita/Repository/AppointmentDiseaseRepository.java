package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentDiseaseRepository extends JpaRepository<AppointmentDisease, Long> {
}
