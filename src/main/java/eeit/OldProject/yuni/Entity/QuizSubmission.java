package eeit.OldProject.yuni.Entity;

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

	public QuizSubmission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizSubmission(Integer submissionId, Double score, Boolean isPassed, LocalDateTime submitAt,
			LocalDateTime updatedAt, User userId, Course courseId) {
		super();
		this.submissionId = submissionId;
		this.score = score;
		this.isPassed = isPassed;
		this.submitAt = submitAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.courseId = courseId;
	}

	public Integer getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Integer submissionId) {
		this.submissionId = submissionId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}

	public LocalDateTime getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(LocalDateTime submitAt) {
		this.submitAt = submitAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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

	@Override
	public String toString() {
		return "QuizSubmission [submissionId=" + submissionId + ", score=" + score + ", isPassed=" + isPassed
				+ ", submitAt=" + submitAt + ", updatedAt=" + updatedAt + ", userId=" + userId + ", courseId="
				+ courseId + "]";
	}
    
    
}
