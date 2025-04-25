package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "appointment_time_continuous")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTimeContinuous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinuousId")
    private Long continuousId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "StartTime", nullable = true)
    private Date startTime;

    @Column(name = "EndTime", nullable = true)
    private Date endTime;

    @Column(name = "FlexibilityNote", columnDefinition = "TEXT", nullable = true)
    private String flexibilityNote;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}

