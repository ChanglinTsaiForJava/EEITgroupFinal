package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_time_multi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTimeMulti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MultiId")
    private Long multiId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "DailyStartTime")
    private LocalTime dailyStartTime;

    @Column(name = "DailyEndTime")
    private LocalTime dailyEndTime;

    @Column(name = "FlexibilityNote")
    private String flexibilityNote;

    @Column(name = "Monday")
    private Boolean monday;

    @Column(name = "Tuesday")
    private Boolean tuesday;

    @Column(name = "Wednesday")
    private Boolean wednesday;

    @Column(name = "Thursday")
    private Boolean thursday;

    @Column(name = "Friday")
    private Boolean friday;

    @Column(name = "Saturday")
    private Boolean saturday;

    @Column(name = "Sunday")
    private Boolean sunday;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}

