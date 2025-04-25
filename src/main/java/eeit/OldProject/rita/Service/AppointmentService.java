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
    private final NotificationService notificationService;
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

    /**
     * 更新預約狀態（如：從 Pending 改成 CaregiverConfirmed 或 Paid）
     * 看護確認預約、顧客付款、服務完成等情境會用到
     */
    public Appointment updateStatus(Long id, Appointment.AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found")); // 若找不到拋錯
        appointment.setStatus(status);
        return appointmentRepository.save(appointment); // 存回資料庫
    }

    /**
     * 刪除預約
     */
    public void deleteById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    /**
     * 顧客勾選「我同意合約條款」後儲存確認狀態
     **/
    public Appointment confirmContract(Long appointmentId) {
        Appointment a = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setContractConfirmed(true);
        return appointmentRepository.save(a);
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

    /**
     * 看護登入後按下「接受預約」：
     * 1️⃣ 將預約狀態更新為 CaregiverConfirmed
     * 2️⃣ 發送 Email 通知顧客確認合約並付款
     */
    public Appointment acceptByCaregiver(Long appointmentId) {
        Appointment a = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        // 將預約狀態設定為 CaregiverConfirmed（已由看護接受）
        a.setStatus(Appointment.AppointmentStatus.CaregiverConfirmed);
        Appointment saved = appointmentRepository.save(a);

        // 補齊完整的顧客與看護資料（為了後續取得 email、姓名等）
        saved.setUser(userRepository.findById(saved.getUserId()).orElse(null));
        saved.setCaregiver(caregiverRepository.findById(saved.getCaregiverId()).orElse(null));

        // ✅ 若顧客的 email 不為 null，則發送 Email 提醒付款
        if (saved.getUser() != null && saved.getUser().getEmailAddress() != null) {
            // 產生提醒付款的 Email 內容
            String content = emailTemplateService.generateContractReminderContent(saved);

            // 呼叫 NotificationService 發送 Email
            notificationService.sendEmail(
                    saved.getUser().getEmailAddress(),              // 收件人
                    "看護已接受您的預約，請確認合約並付款", // 主旨
                    content                                          // 內文
            );

        }

        // 回傳更新後的預約物件
        return saved;
    }

    /**
     * 顧客「完成付款」後：
     * 1️⃣ 將預約狀態更新為 Paid
     * 2️⃣ 查詢對應的付款紀錄
     * 3️⃣ 發送 Email 通知顧客，內含收據與合約下載連結
     */
    public Appointment markAsPaid(Long appointmentId) throws ChangeSetPersister.NotFoundException {
        // 1️⃣ 查詢預約資料，若找不到則丟出錯誤
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // 2️⃣ 更新預約狀態為已付款
        appointment.setStatus(Appointment.AppointmentStatus.Paid);
        Appointment saved = appointmentRepository.save(appointment);

        // 3️⃣ 查出顧客與看護詳細資料（後續 email 內容要用）
        saved.setUser(userRepository.findById(saved.getUserId()).orElse(null));
        saved.setCaregiver(caregiverRepository.findById(saved.getCaregiverId()).orElse(null));

        // 4️⃣ 查詢該預約對應的付款資料（Payment.paymentReferenceType = Appointment）
        Payment payment = paymentRepository.findByReferenceIdAndPaymentReferenceType(
                saved.getAppointmentId(),
                PaymentReferenceType.Appointment
        ).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // 5️⃣ 若顧客有填 email，寄送收據與合約連結通知信
        if (saved.getUser() != null && saved.getUser().getEmailAddress() != null) {
            // 產生付款成功的 email 內容
            String content = emailTemplateService.generateAppointmentPaidContent(saved, payment);

            // 寄出 email（收據 + 合約連結）
            notificationService.sendEmail(
                    saved.getUser().getEmailAddress(),            // 收件人
                    "付款完成通知 - Care+ 預約服務",               // 主旨
                    content                                       // 內文
            );
        }
        return saved;
    }



}
