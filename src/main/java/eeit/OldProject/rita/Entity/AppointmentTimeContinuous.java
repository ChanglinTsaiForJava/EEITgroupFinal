package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "appointment_time_continuous")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTimeContinuous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinuousId")
    private Long continuousId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "StartTime")
    private Date startTime;

    @Column(name = "EndTime")
    private Date endTime;

    @Column(name = "FlexibilityNote")
    private String flexibilityNote;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}

