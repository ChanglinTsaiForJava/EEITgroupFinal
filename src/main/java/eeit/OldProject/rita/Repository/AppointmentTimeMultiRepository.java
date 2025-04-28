package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentTimeMulti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentTimeMultiRepository extends JpaRepository<AppointmentTimeMulti, Long> {
    // 查詢與特定 appointmentId 相關聯的所有 AppointmentTimeMulti
    List<AppointmentTimeMulti> findByAppointmentId(Long appointmentId);
    void deleteByAppointmentId(Long appointmentId);


    @Query("""
    SELECT DISTINCT a.caregiverId
    FROM AppointmentTimeMulti m
    JOIN Appointment a ON m.appointmentId = a.appointmentId
    WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
      AND (m.startDate <= :endDate AND m.endDate >= :startDate)
""")
    List<Long> findOccupiedCaregiversInMulti(
            @Param("startDate") java.time.LocalDate startDate,
            @Param("endDate") java.time.LocalDate endDate
    );
}
