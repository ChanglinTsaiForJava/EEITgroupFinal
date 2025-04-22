package eeit.OldProject.yuni.entity;
import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress", schema ="final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('not_started', 'in_progress', 'completed')")
    private Status status;
    private Boolean isCompleted;
    private LocalDateTime completionDate;
    private Float lastWatched;

//    private Long userId;
    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User userId;

//    private Integer courseId;
    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course courseId;

//    private Integer chapterId;
    @ManyToOne
    @JoinColumn(name = "ChapterId", referencedColumnName = "ChapterId")
    private Chapter chapterId;

}
