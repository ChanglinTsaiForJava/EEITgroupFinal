package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "appointment_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "ServiceId")
    private Long serviceId;

    @Column(name = "CustomDescription")
    private String customDescription;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "ServiceId", insertable = false, updatable = false)
    private Service service;
}

