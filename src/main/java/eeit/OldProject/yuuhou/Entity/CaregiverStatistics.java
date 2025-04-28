package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "caregiver_statistics")
public class CaregiverStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CaregiverStatisticsId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CareGiverId", nullable = false)
    private Caregiver caregiver;

    private Integer month;
    private Integer year;
    private Integer callCount = 0;
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
