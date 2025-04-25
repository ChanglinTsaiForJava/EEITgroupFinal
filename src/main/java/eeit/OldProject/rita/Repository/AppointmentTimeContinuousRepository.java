package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentTimeContinuous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentTimeContinuousRepository extends JpaRepository<AppointmentTimeContinuous, Long> {
    // 查詢與特定 appointmentId 相關聯的所有 AppointmentTimeContinuous
    List<AppointmentTimeContinuous> findByAppointmentId(Long appointmentId);
}
