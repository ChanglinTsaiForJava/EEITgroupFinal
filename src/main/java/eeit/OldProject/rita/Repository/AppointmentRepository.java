package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId (Long userId);
    /**
     * 根據使用者 ID 查找該使用者所有預約紀錄
     * 顧客端查詢「我預約過哪些看護」
     * SELECT * FROM appointment WHERE user_id = ?
     */
    List<Appointment> findByCaregiverIdAndStatus (Long caregiverId, Appointment.AppointmentStatus status);
    /**
     * 根據看護 ID 和預約狀態查詢該看護特定狀態的預約清單
     * 看護端查詢「我有哪些預約還沒處理 / 已付款 / 已完成」
     * SELECT * FROM appointment WHERE caregiver_id = ? AND status = ?
     */
    
    List<Appointment> findByAppointmentId(Long appointmentId);
}
