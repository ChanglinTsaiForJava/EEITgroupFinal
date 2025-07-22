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
         * 1. 顧客勾選「同意合約條款」後儲存確認狀態
         **/
        public Appointment confirmContract(Long appointmentId) {
            Appointment a = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            a.setContractConfirmed(true);
            return appointmentRepository.save(a);
        }

        /**
         * 2. 將預約狀態更新為 Paid
         */
        public Appointment markAsPaid(Long appointmentId) throws ChangeSetPersister.NotFoundException {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            appointment.setStatus(Appointment.AppointmentStatus.Paid);
            return appointmentRepository.save(appointment);
        }

        /**
         * 3. 單純更新預約狀態（所有都可更改）
         *    後台更新使用
         */
        public Appointment updateStatus(Long id, Appointment.AppointmentStatus status) {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }

    }
