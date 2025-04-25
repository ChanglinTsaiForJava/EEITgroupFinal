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

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Thumbnail")
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private NewsCategory category;

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
    private Byte status;

    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "ViewCount")
    private Integer viewCount;

    @Column(name = "Tags")
    private String tags;
}