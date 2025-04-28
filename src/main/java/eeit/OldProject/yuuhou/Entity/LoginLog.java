package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "login_log")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogId")
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CaregiverId", nullable = false)
    private Caregiver caregiver;

    private LocalDateTime loginTime = LocalDateTime.now();
    private String ipAddress;
    @Column(columnDefinition = "TEXT") private String deviceInfo;
}
