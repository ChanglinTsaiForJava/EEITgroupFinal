package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Entity.Payment;
import eeit.OldProject.rita.Entity.PaymentReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 根據 referenceId（也就是 appointmentId）與 referenceType（Appointment 或 Order）來找付款紀錄
    Optional<Payment> findByReferenceIdAndPaymentReferenceType(Long referenceId, PaymentReferenceType referenceType);

}
