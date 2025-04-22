package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name= "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "CaregiverId")
    private Long caregiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TimeType")
    private TimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private AppointmentStatus status;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "LocationType")
    private LocationType locationType;

    @Column(name = "Address")
    private String address;

    @Column(name = "HospitalName")
    private String hospitalName;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "CaregiverId", insertable = false, updatable = false)
//    private Caregiver caregiver;
}
enum TimeType {
    continuous,
    multi
}

enum AppointmentStatus {
    Pending,
    CaregiverConfirmed,
    Paid,
    Completed,
    Cancelled
}

enum LocationType {
    hospital,
    home
}