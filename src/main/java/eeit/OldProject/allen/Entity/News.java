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
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NewsId")
    private Integer newsId;

    @Column(name = "Title", nullable = false, length = 255)
    private String title;

    @Column(name = "Thumbnail", length = 255)
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private NewsCategory category;

    @Column(name = "CreateBy", nullable = false, length = 100)
    private String createBy;

    @Column(name = "CreateAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "PublishAt")
    private LocalDateTime publishAt;

    @Column(name = "ModifyBy", length = 100)
    private String modifyBy;

    @Column(name = "ModifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "Status", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer status;

    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "ViewCount", columnDefinition = "INT DEFAULT 0")
    private Integer viewCount;

    @Column(name = "Tags", length = 255)
    private String tags;
}
