package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "payment_records")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentId")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CaregiverId", nullable = false)
    private Caregiver caregiver;

    private Long appointmentId;        // 先用單純欄位；如有 Appointment 實體再改 ManyToOne
    private BigDecimal amount;
    private LocalDateTime paidAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String notes;
}
