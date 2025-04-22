package eeit.OldProject.allen.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    private NewsCategory category;

    @Column(name = "CreateBy", nullable = false, length = 100)
    private String createBy;

    @Column(name = "CreateAt")
    private LocalDateTime createAt;

    @Column(name = "PublishAt")
    private LocalDateTime publishAt;

    @Column(name = "ModifyBy", length = 100)
    private String modifyBy;

    @Column(name = "ModifyAt")
    private LocalDateTime modifyAt;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "ViewCount")
    private Integer viewCount;

    @Column(name = "Tags", length = 255)
    private String tags;
}