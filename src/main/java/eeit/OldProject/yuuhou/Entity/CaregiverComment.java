package eeit.OldProject.yuuhou.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "caregiver_comments")
public class CaregiverComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentId")
    private Long commentId;

    /* ↙︎ 連回 Caregiver */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CaregiverId", nullable = false)
    private Caregiver caregiver;

    @Lob
    @Column(name = "CommentText", nullable = false, columnDefinition = "TEXT")
    private String commentText;

    private Integer rate;                           // 1~5
    private LocalDateTime commentTime = LocalDateTime.now();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String replyText;

    private LocalDateTime replyTime;
}
