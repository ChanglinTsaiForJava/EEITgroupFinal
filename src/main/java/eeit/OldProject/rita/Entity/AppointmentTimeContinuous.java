package eeit.OldProject.rita.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnore
    private Appointment appointment;
}

