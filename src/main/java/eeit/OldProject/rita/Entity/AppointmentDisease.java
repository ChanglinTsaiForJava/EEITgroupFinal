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
    @JsonIgnore
    private Appointment appointment;
}

