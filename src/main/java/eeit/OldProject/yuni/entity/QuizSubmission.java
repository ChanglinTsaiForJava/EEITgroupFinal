package eeit.OldProject.yuni.entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_submission", schema = "final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submissionId;

    private Double score;

    private Boolean isPassed;

    @CreationTimestamp
    private LocalDateTime submitAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private Long userId;
    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course courseId;
}
