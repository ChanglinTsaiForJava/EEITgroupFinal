package eeit.OldProject.entity.steve;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientID")
    private Integer patientId;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Birthday")
    private LocalDate birthday;

    @Column(name = "Gender")
    private Byte gender;

    @Column(name = "PatientDetail", length = 500)
    private String patientDetail;

    @Column(name = "EmergencyContact", length = 14)
    private String emergencyContact;

    @Column(name = "Address", length = 50)
    private String address;

    @Column(name = "Allergies", length = 100)
    private String allergies;

    @Column(name = "FamilyHistory", length = 100)
    private String familyHistory;

    @Column(name = "CurrentMedications", length = 100)
    private String currentMedications;

    @Column(name = "VaccinationHistory", length = 100)
    private String vaccinationHistory;

    @Column(name = "LifestyleAndRiskFactors", length = 100)
    private String lifestyleAndRiskFactors;
}

