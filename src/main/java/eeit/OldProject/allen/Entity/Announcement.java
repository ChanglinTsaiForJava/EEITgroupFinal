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

    @Column(name = "Title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private AnnouncementCategory category;

    @Column(name = "CreateBy", nullable = false)
    private String createBy;

    @Column(name = "CreateAt")
    private LocalDateTime createAt;

    @Column(name = "PublishAt")
    private LocalDateTime publishAt;

    @Column(name = "ModifyBy")
    private String modifyBy;

    @Column(name = "ModifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "ViewCount")
    private Integer viewCount;

    @Column(name = "PinTop")
    private Integer pinTop;

    @Column(name = "Content")
    private String content;
}