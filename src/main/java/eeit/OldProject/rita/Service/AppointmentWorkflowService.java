    package eeit.OldProject.rita.Service;

    import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Entity.Payment;
import eeit.OldProject.rita.Entity.PaymentReferenceType;
import eeit.OldProject.rita.Entity.PaymentStatus;
import eeit.OldProject.rita.Repository.AppointmentRepository;
import eeit.OldProject.rita.Repository.PaymentRepository;
import eeit.OldProject.steve.Repository.UserRepository;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    public class AppointmentWorkflowService {

        private final AppointmentRepository appointmentRepository;
        private final CaregiversRepository caregiverRepository;
        private final UserRepository userRepository;
        private final EmailTemplateService emailTemplateService;
        private final NotificationService notificationService;
        private final PaymentRepository paymentRepository;

        /**
         * 看護登入後按下「接受預約」：
         * 1️⃣ 將預約狀態更新為 CaregiverConfirmed
         * 2️⃣ 建立payment及綠界交易編號
         * 3️⃣ 發送 Email 通知顧客確認合約並付款
         */
        @Transactional
        public Appointment acceptAndCreatePayment(Long appointmentId) {
            // 1. 查詢預約
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            // 2. 更新預約狀態為 CaregiverConfirmed（看護已接受）
            appointment.setStatus(Appointment.AppointmentStatus.CaregiverConfirmed);
            Appointment savedAppointment = appointmentRepository.save(appointment);

            // 3. 補齊完整的 User、Caregiver 資料（為了後面寄信）
            savedAppointment.setUser(userRepository.findById(savedAppointment.getUserId()).orElse(null));
            savedAppointment.setCaregiver(caregiverRepository.findById(savedAppointment.getCaregiverId()).orElse(null));

            // 4. 建立對應的 Payment 紀錄（status 預設 Pending）
            String merchantTradeNo = generateMerchantTradeNo();

            Payment payment = Payment.builder()
                    .referenceId(savedAppointment.getAppointmentId().intValue()) // 這邊是 appointmentId
                    .userId(savedAppointment.getUserId())
                    .amount(savedAppointment.getTotalPrice())
                    .paymentMethod("ECPay") // 這邊固定寫 ECPay
                    .paymentStatus(PaymentStatus.Pending) // 付款狀態是 Pending
                    .merchantTradeNo(merchantTradeNo) // 綠界交易編號
                    .paymentReferenceType(PaymentReferenceType.Appointment) // 關聯到 Appointment
                    .finalAmount(savedAppointment.getTotalPrice()) // 最後付款金額 = 總價
                    .tradeDate(new Date()) // 建立付款時間
                    .build();
            try {
                System.out.println("準備建立 Payment：" + payment);
                paymentRepository.save(payment);
                System.out.println("✅ Payment 建立完成！");
            } catch (Exception e) {
                System.out.println("❌ 建立 Payment 失敗！");
                e.printStackTrace();
            }

            // 5. 發送 Email 通知顧客去確認合約 & 付款
            if (savedAppointment.getUser() != null && savedAppointment.getUser().getEmailAddress() != null) {
                String content = emailTemplateService.generateContractReminderContent(savedAppointment);

                notificationService.sendEmail(
                        savedAppointment.getUser().getEmailAddress(),
                        "看護已接受您的預約，請確認合約並付款",
                        content
                );
            }
            return savedAppointment;
        }
     // 將 generateMerchantTradeNo() 方法寫在這裡
        private String generateMerchantTradeNo() {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            return "ECPAY" + timestamp + uuid;
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
         * 顧客「完成付款」後：
         * 1️⃣ 將預約狀態更新為 Paid
         * 2️⃣ 查詢對應的付款紀錄
         * 3️⃣ 發送 Email 通知顧客，內含收據與合約下載連結
         */
        public Appointment markAsPaid(Long appointmentId) throws ChangeSetPersister.NotFoundException {
            // 1️⃣ 查詢預約資料，若找不到則丟出錯誤
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

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
            ).orElseThrow(ChangeSetPersister.NotFoundException::new);

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

        /**
         * 單純更新預約狀態（所有都可更改）
         * 後台更新使用
         */
        public Appointment updateStatus(Long id, Appointment.AppointmentStatus status) {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found")); // 若找不到拋錯
            appointment.setStatus(status);
            return appointmentRepository.save(appointment); // 存回資料庫
        }

    }
