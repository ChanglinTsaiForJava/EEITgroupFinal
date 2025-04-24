package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "appointment_physical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPhysical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhysicalId")
    private Long physicalId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "CustomDescription")
    private String customDescription;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "PhysicalId", insertable = false, updatable = false)
    private Physical physical;

    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}
