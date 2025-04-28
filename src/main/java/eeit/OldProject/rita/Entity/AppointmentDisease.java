package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment_disease")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name = "DiseaseId")
    private Long diseaseId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "CustomDescription", columnDefinition = "TEXT", nullable = true)
    private String customDescription;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "DiseaseId", insertable = false, updatable = false)
    private Disease disease;

    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    private Appointment appointment;
}

