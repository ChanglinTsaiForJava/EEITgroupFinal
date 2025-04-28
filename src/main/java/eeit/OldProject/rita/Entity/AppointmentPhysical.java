//package eeit.OldProject.rita.Entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "appointment_physical")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class AppointmentPhysical {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long Id;
//
//
//    @Column(name = "PhysicalId")
//    private Long physicalId;
//
//    @Column(name = "AppointmentId")
//    private Long appointmentId;
//
//    @Column(name = "CustomDescription", columnDefinition = "TEXT", nullable = true)
//    private String customDescription;
//
//    // Relationships
//    @ManyToOne
//    @JoinColumn(name = "PhysicalId", insertable = false, updatable = false)
//    private Physical physical;
//
//    @ManyToOne
//    @JoinColumn(name = "AppointmentId", insertable = false, updatable = false)
//    private Appointment appointment;
//}
