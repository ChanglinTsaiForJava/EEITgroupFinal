package eeit.OldProject.rita.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eeit.OldProject.rita.Entity.AppointmentTimeMulti;

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
    
 // 以下是「依照星期幾 + 日期」查詢
    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.monday = true
    """)
    List<Long> findOccupiedCaregiversOnMonday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.tuesday = true
    """)
    List<Long> findOccupiedCaregiversOnTuesday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.wednesday = true
    """)
    List<Long> findOccupiedCaregiversOnWednesday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.thursday = true
    """)
    List<Long> findOccupiedCaregiversOnThursday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.friday = true
    """)
    List<Long> findOccupiedCaregiversOnFriday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.saturday = true
    """)
    List<Long> findOccupiedCaregiversOnSaturday(@Param("targetDate") LocalDate targetDate);

    @Query("""
        SELECT DISTINCT a.caregiverId
        FROM AppointmentTimeMulti m
        JOIN Appointment a ON m.appointmentId = a.appointmentId
        WHERE a.status IN ('Pending', 'CaregiverConfirmed', 'Paid')
          AND m.startDate <= :targetDate AND m.endDate >= :targetDate
          AND m.sunday = true
    """)
    List<Long> findOccupiedCaregiversOnSunday(@Param("targetDate") LocalDate targetDate);
}
