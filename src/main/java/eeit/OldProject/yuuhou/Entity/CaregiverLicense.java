package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "caregiver_licenses")
public class CaregiverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "licenseId")
    private Long licenseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CareGiverId", nullable = false)
    private Caregiver caregiver;

    private String licenseName;
    private String filePath;
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
