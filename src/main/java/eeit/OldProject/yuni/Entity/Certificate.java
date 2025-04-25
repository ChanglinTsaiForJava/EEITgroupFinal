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
@Table(name="certificate", schema = "final")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer certificateId;
    private LocalDateTime issuedAt;

//    private Long userId;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course course;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    
	public Certificate(Integer certificateId, LocalDateTime issuedAt, User userId, Course course,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.certificateId = certificateId;
		this.issuedAt = issuedAt;
		this.userId = userId;
		this.course = course;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		
	
	}


	public Certificate() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getCertificateId() {
		return certificateId;
	}


	public void setCertificateId(Integer certificateId) {
		this.certificateId = certificateId;
	}


	public LocalDateTime getIssuedAt() {
		return issuedAt;
	}


	public void setIssuedAt(LocalDateTime issuedAt) {
		this.issuedAt = issuedAt;
	}


	public User getUserId() {
		return userId;
	}


	public void setUserId(User userId) {
		this.userId = userId;
	}


	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
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


	@Override
	public String toString() {
		return "Certificate [certificateId=" + certificateId + ", issuedAt=" + issuedAt + ", userId=" + userId
				+ ", course=" + course + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	
    
    
    
    
    
    
}