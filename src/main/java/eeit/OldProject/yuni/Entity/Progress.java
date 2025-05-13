package eeit.OldProject.yuni.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress", schema ="final")

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
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "progresses"})
    private User userId;

//    private Integer courseId;
    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "chapters", "progresses"})
    private Course courseId;

//    private Integer chapterId;
    @ManyToOne
    @JoinColumn(name = "ChapterId", referencedColumnName = "ChapterId")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "course", "progresses"})
    private Chapter chapterId;

	public Progress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Progress(Integer progressId, Status status, Boolean isCompleted, LocalDateTime completionDate,
			Float lastWatched, User userId, Course courseId, Chapter chapterId) {
		super();
		this.progressId = progressId;
		this.status = status;
		this.isCompleted = isCompleted;
		this.completionDate = completionDate;
		this.lastWatched = lastWatched;
		this.userId = userId;
		this.courseId = courseId;
		this.chapterId = chapterId;
	}

	public Integer getProgressId() {
		return progressId;
	}

	public void setProgressId(Integer progressId) {
		this.progressId = progressId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public Float getLastWatched() {
		return lastWatched;
	}

	public void setLastWatched(Float lastWatched) {
		this.lastWatched = lastWatched;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public Chapter getChapterId() {
		return chapterId;
	}

	public void setChapterId(Chapter chapterId) {
		this.chapterId = chapterId;
	}

	@Override
	public String toString() {
		return "Progress [progressId=" + progressId + ", status=" + status + ", isCompleted=" + isCompleted
				+ ", completionDate=" + completionDate + ", lastWatched=" + lastWatched + ", userId=" + userId
				+ ", courseId=" + courseId + ", chapterId=" + chapterId + "]";
	}

    
    
}
