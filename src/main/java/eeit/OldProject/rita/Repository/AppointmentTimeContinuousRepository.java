package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentTimeContinuous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentTimeContinuousRepository extends JpaRepository<AppointmentTimeContinuous, Long> {
    // 查詢與特定 appointmentId 相關聯的所有 AppointmentTimeContinuous
    List<AppointmentTimeContinuous> findByAppointmentId(Long appointmentId);
    void deleteByAppointmentId(Long appointmentId);


    @Query("""
    SELECT DISTINCT a.caregiverId
    FROM AppointmentTimeContinuous c
    JOIN Appointment a ON c.appointmentId = a.appointmentId
    WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
      AND (c.startTime < :endTime AND c.endTime > :startTime)
""")
    List<Long> findOccupiedCaregiversInContinuous(
            @Param("startTime") java.util.Date startTime,
            @Param("endTime") java.util.Date endTime
    );
}
