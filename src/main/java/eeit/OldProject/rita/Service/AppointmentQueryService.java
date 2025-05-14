package eeit.OldProject.rita.Service;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceItemRepository appointmentServiceItemRepository;
    private final AppointmentPhysicalRepository appointmentPhysicalRepository;
    private final AppointmentDiseaseRepository appointmentDiseaseRepository;
    private final AppointmentTimeContinuousRepository appointmentTimeContinuousRepository;
    private final AppointmentTimeMultiRepository appointmentTimeMultiRepository;
    
    /**
     * 根據"預約" ID 查詢單一預約資料
     * 前端點擊查看預約詳情時可使用
     */
    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    /**
     * 根據"使用者" ID 查詢該使用者的所有預約
     * 顧客查看自己的預約紀錄用
     */
    public List<Appointment> getByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> findByCaregiver_CaregiverId(Long caregiverId){
    	return appointmentRepository.findByCaregiver_CaregiverId(caregiverId);
    }
     

    /**
     * 刪除預約
     */
    @Transactional
    public void deleteById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }

        // 🔥 先刪掉所有子資料
        appointmentServiceItemRepository.deleteByAppointmentId(id);
        appointmentPhysicalRepository.deleteByAppointmentId(id);
        appointmentDiseaseRepository.deleteByAppointmentId(id);
        appointmentTimeContinuousRepository.deleteByAppointmentId(id);
        appointmentTimeMultiRepository.deleteByAppointmentId(id);

        // 🗑️ 最後才刪 appointment 本體
        appointmentRepository.deleteById(id);
    }


}
