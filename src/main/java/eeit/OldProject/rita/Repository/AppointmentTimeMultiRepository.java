package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentTimeMulti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentTimeMultiRepository extends JpaRepository<AppointmentTimeMulti, Long> {
    // 查詢與特定 appointmentId 相關聯的所有 AppointmentTimeMulti
    List<AppointmentTimeMulti> findByAppointmentId(Long appointmentId);
}
