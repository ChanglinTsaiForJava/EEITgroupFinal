package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Entity
    @Table(name = "appointment_disease")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AppointmentDisease {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "DiseaseId")
        private Long diseaseId;

        @Column(name = "AppointmentId")
        private Long appointmentId;

        @Column(name = "CustomDescription")
        private String customDescription;

        // Relationships
        @ManyToOne
        @JoinColumn(name = "DiseaseId", insertable = false, updatable = false)
        private Disease disease;

        @ManyToOne
        @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
        private Appointment appointment;
    }

