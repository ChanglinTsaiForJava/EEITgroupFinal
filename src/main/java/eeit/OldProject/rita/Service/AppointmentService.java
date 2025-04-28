package eeit.OldProject.rita.Service;

import eeit.OldProject.rita.Entity.*;
import eeit.OldProject.rita.Repository.*;
import eeit.OldProject.steve.Repository.UserRepository;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentDiseaseRepository appointmentDiseaseRepository;
    private final AppointmentPhysicalRepository appointmentPhysicalRepository;
    private final AppointmentServiceItemRepository appointmentServiceItemRepository;
    private final AppointmentTimeContinuousRepository continuousRepository;
    private final AppointmentTimeMultiRepository multiRepository;
    private final TimeCalculationService timeCalculationService;
    private final EmailTemplateService emailTemplateService;
    private final NotificationServiceRita notificationService;
    private final CaregiversRepository caregiverRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;


    /**
     * 新增預約：初始化預約狀態為 Pending，並儲存至資料庫
     * 顧客送出預約需求時
     */
    public Appointment createWithDetails(
            Appointment appointment,
            List<AppointmentDisease> diseases,
            List<AppointmentPhysical> physicals,
            List<AppointmentServiceItem> services,
            Optional<AppointmentTimeContinuous> continuous,
            Optional<AppointmentTimeMulti> multi
    ) {
        // ✅ 必填欄位
        if (appointment.getTotalPrice() == null || appointment.getLocationType() == null) {
            throw new IllegalArgumentException("金額與地點類型為必填");
        }
        if (appointment.getUserId() == null || appointment.getCaregiverId() == null) {
            throw new IllegalArgumentException("缺少使用者 ID 或看護 ID");
        }
        if (appointment.getPatientId() == null) {
            throw new IllegalArgumentException("缺少病人 ID (PatientId)");
        }

        // ✅ 至少選擇一項服務
        if (services == null || services.isEmpty()) {
            throw new IllegalArgumentException("請選擇至少一項照護服務");
        }

        // ✅ 服務時間邏輯驗證
        continuous.ifPresent(c -> {
            if (c.getStartTime() == null || c.getEndTime() == null) {
                throw new IllegalArgumentException("連續時間類型必須填寫開始與結束時間");
            }
            if (c.getStartTime().after(c.getEndTime())) {
                throw new IllegalArgumentException("服務開始時間不可晚於結束時間");
            }
        });

        multi.ifPresent(m -> {
            if (m.getStartDate() == null || m.getEndDate() == null || m.getDailyStartTime() == null || m.getDailyEndTime() == null) {
                throw new IllegalArgumentException("多時段類型需填寫完整日期與時間");
            }
            if (m.getStartDate().isAfter(m.getEndDate())) {
                throw new IllegalArgumentException("多時段的起始日不可晚於結束日");
            }
            if (m.getDailyStartTime().isAfter(m.getDailyEndTime())) {
                throw new IllegalArgumentException("每日服務開始時間不可晚於結束時間");
            }
        });

        // 設定預設狀態
        appointment.setStatus(Appointment.AppointmentStatus.Pending);

        // 儲存主表 appointment
        Appointment saved = appointmentRepository.save(appointment);
        Long appointmentId = saved.getAppointmentId();


        // 子表資料：疾病
        if (diseases != null && !diseases.isEmpty()) {
            diseases.forEach(d -> {
                d.setAppointmentId(appointmentId);
                appointmentDiseaseRepository.save(d);
            });
        }

        // 子表資料：身體狀況
        if (physicals != null && !physicals.isEmpty()) {
            physicals.forEach(p -> {
                p.setAppointmentId(appointmentId);
                appointmentPhysicalRepository.save(p);
            });
        }

        // 子表資料：服務項目
        if (services != null && !services.isEmpty()) {
            services.forEach(s -> {
                s.setAppointmentId(appointmentId);
                appointmentServiceItemRepository.save(s);
            });
        }

        // 子表資料：連續時段（可選）
        continuous.ifPresent(c -> {
            c.setAppointmentId(appointmentId);
            continuousRepository.save(c);
        });

        // 子表資料：多時段（可選）
        multi.ifPresent(m -> {
            m.setAppointmentId(appointmentId);
            multiRepository.save(m);
        });

        // 取得完整的 Caregiver 與 User 資料（為了抓出 lineToken 和 userName）
        saved.setCaregiver(caregiverRepository.findById(saved.getCaregiverId()).orElse(null));
        saved.setUser(userRepository.findById(saved.getUserId()).orElse(null));

        if (saved.getCaregiver() != null && saved.getCaregiver().getEmail() != null) {
            String subject = "【Care+ 看護預約通知】您有一筆新的預約申請";
            String content = emailTemplateService.generateNewAppointmentNotifyContent(saved);

            notificationService.sendEmail(
                    saved.getCaregiver().getEmail(),
                    subject,
                    content
            );
        }

        return saved;
    }

    /**
     * 計算總時數並返回格式化的時間
     **/
    public String calculateTotalTime(Long appointmentId) {
        // 從資料庫中查詢 AppointmentTimeContinuous 和 AppointmentTimeMulti
        List<AppointmentTimeContinuous> continuousTimes = continuousRepository.findByAppointmentId(appointmentId);
        List<AppointmentTimeMulti> multiTimes = multiRepository.findByAppointmentId(appointmentId);

        // 使用 TimeCalculationService 來計算總時間
        return timeCalculationService.calculateTotalTime(continuousTimes, multiTimes);  // 返回 "X天 Y小時"
    }

    public void sendPaidEmail(Appointment appointment, Payment payment) {
        String content = emailTemplateService.generateAppointmentPaidContent(appointment, payment);
        // 寄信邏輯這邊可以整合 JavaMailSender 或其他寄信工具
        System.out.println("寄出 email: \n" + content);
    }



}
