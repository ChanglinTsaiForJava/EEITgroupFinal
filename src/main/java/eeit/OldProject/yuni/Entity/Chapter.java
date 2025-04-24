package eeit.OldProject.yuni.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chapter", schema = "final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    private String title;

    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('video', 'article')")
    private ContentType contentType;

    private String contentUrl;

    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course course;
}
