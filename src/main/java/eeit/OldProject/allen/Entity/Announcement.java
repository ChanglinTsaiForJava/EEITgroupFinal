package eeit.OldProject.allen.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnnouncementID")
    private Integer announcementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    private AnnouncementCategory category;

    @Column(name = "Title", nullable = false, length = 100)
    private String title;

    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "CreateBy", nullable = false, length = 100)
    private String createBy;

    @CreationTimestamp
    @Column(name = "CreateAt", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "PublishAt")
    private LocalDateTime publishAt;

    @Column(name = "ModifyBy", nullable = false, length = 100)
    private String modifyBy;

    @Column(name = "ModifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "ViewCount")
    private Integer viewCount;

    @Column(name = "PinTop")
    private Integer pinTop;
}