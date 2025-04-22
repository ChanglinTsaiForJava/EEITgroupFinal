package eeit.OldProject.yuuhou.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointment_time_continuous")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaregiversAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinuousId")
    private Long continuousId;

    @Column(name = "AppointmentId", nullable = false)
    private Long appointmentId;

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "EndTime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "FlexibilityNote")
    private String flexibilityNote;

    // 若妳有設 Appointment entity，可加下面這段做關聯：
    // @ManyToOne
    // @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    // private Appointment appointment;
}
