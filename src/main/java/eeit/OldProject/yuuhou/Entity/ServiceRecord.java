package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_record")
@Data
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordId")
    private Long recordId;

    @Column(name = "AppointmentId", nullable = false)
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CaregiverId", referencedColumnName = "CareGiverId")
    private Caregiver caregiver;
    
    
//    @Column(name = "CaregiverId", nullable = false)
//    private Long caregiverId;

    @Column(name = "ActualStartTime", nullable = false)
    private LocalDateTime actualStartTime;

    @Column(name = "ActualEndTime")
    private LocalDateTime actualEndTime;

    @Column(name = "Notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
