package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "user", "comment", "reactions", "reports" })
@Entity
@Table(name = "reply", schema = "final")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReplyId")
	private Long replyId;

	@Lob
	@Column(name = "Content", columnDefinition = "LONGTEXT")
	private String content;

	@Column(name = "CreatedAt")
	private LocalDateTime createdAt;

	@Column(name = "ModifiedAt")
	private LocalDateTime modifiedAt;

	@Column(name = "Status")
	private Byte status;

	@ManyToOne
	@JoinColumn(name = "UserId")
	@JsonIgnoreProperties("replies")
	private User user;

	@ManyToOne
	@JoinColumn(name = "CommentId")
	@JsonIgnoreProperties("replies")
	private Comment comment;

	@OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("reply")
	private List<ReplyReaction> reactions;

	@OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("reply")
	private List<ReplyReport> reports;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.modifiedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedAt = LocalDateTime.now();
	}
}
