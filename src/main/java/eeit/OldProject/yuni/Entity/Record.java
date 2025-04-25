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
@Table(name = "record", schema = "final")

public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    private LocalDateTime date;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private Long userId;
    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User userId;

//    private Integer courseId; //
    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course courseId;

	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Record(Integer recordId, LocalDateTime date, LocalDateTime createdAt, LocalDateTime updatedAt, User userId,
			Course courseId) {
		super();
		this.recordId = recordId;
		this.date = date;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.courseId = courseId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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
		return "Record [recordId=" + recordId + ", date=" + date + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", userId=" + userId + ", courseId=" + courseId + "]";
	}



}
