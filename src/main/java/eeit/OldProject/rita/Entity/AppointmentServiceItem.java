package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment_service_Item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "ServiceId")
    private Long serviceId;

    @Column(name = "CustomDescription", columnDefinition = "TEXT", nullable = true)
    private String customDescription;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "ServiceId", insertable = false, updatable = false)
    private ServiceItem serviceItem;
}

