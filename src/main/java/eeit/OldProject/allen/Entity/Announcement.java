package eeit.OldProject.allen.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private AnnouncementCategory category;

    @Column(name = "Title", nullable = false, length = 100)
    private String title;

    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "CreateBy", nullable = false, length = 100)
    private String createBy;

    @Column(name = "CreateAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "PublishAt")
    private LocalDateTime publishAt;

    @Column(name = "ModifyBy", nullable = false, length = 100)
    private String modifyBy;

    @Column(name = "ModifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "Status", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer status;

    @Column(name = "ViewCount", columnDefinition = "INT DEFAULT 0")
    private Integer viewCount;

    @Column(name = "PinTop", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer pinTop;
}