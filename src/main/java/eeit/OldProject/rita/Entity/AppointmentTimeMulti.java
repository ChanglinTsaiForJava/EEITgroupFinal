package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_time_multi")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTimeMulti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MultiId")
    private Long multiId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "StartDate", nullable = true)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = true)
    private LocalDate endDate;

    @Column(name = "DailyStartTime", nullable = true)
    private LocalTime dailyStartTime;

    @Column(name = "DailyEndTime", nullable = true)
    private LocalTime dailyEndTime;

    @Column(name = "FlexibilityNote", columnDefinition = "TEXT", nullable = true)
    private String flexibilityNote;

    @Column(name = "Monday", nullable = true)
    private Boolean monday;

    @Column(name = "Tuesday", nullable = true)
    private Boolean tuesday;

    @Column(name = "Wednesday", nullable = true)
    private Boolean wednesday;

    @Column(name = "Thursday", nullable = true)
    private Boolean thursday;

    @Column(name = "Friday", nullable = true)
    private Boolean friday;

    @Column(name = "Saturday", nullable = true)
    private Boolean saturday;

    @Column(name = "Sunday", nullable = true)
    private Boolean sunday;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}

