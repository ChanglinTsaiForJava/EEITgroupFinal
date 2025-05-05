package eeit.OldProject.steve.Entity;

import eeit.OldProject.yuni.Entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_course")
public class Favorite_course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavCourseId")
    private Long favCourseId;

    @Column(name = "ArchivedDate")
    private LocalDateTime archivedDate;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "CourseId")
    private Integer courseId; // ✅ 改為 Integer

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CourseId", referencedColumnName = "courseId", insertable = false, updatable = false)
    private Course course;
}




