package eeit.OldProject.yuni.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification", schema = "final")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User userId;

	public Notification(Integer notificationId, String message, Boolean isRead, LocalDateTime createdAt, User userId) {
		super();
		this.notificationId = notificationId;
		this.message = message;
		this.isRead = isRead;
		this.createdAt = createdAt;
		this.userId = userId;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", message=" + message + ", isRead=" + isRead
				+ ", createdAt=" + createdAt + ", userId=" + userId + ", getNotificationId()=" + getNotificationId()
				+ ", getMessage()=" + getMessage() + ", getIsRead()=" + getIsRead() + ", getCreatedAt()="
				+ getCreatedAt() + ", getUserId()=" + getUserId() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}


    
    
}
