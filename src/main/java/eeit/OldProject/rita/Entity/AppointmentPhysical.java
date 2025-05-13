package eeit.OldProject.rita.Entity;

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
@Table(name = "appointment_physical")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPhysical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(name = "PhysicalId")
    private Long physicalId;

    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "CustomDescription", columnDefinition = "TEXT", nullable = true)
    private String customDescription;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "PhysicalId", insertable = false, updatable = false)
    private Physical physical;

    @ManyToOne
    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
    @JsonIgnore
    private Appointment appointment;
}
