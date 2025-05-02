package eeit.OldProject.daniel.entity.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = {"user","comment"})
@Entity
@Table(name = "comment_reaction", schema = "final")
public class CommentReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentReactionId")
    private Long commentReactionId;

    @Column(name = "CommentReaction")
    private Byte commentReaction;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonIgnoreProperties("commentReactions")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CommentId")
    @JsonIgnoreProperties("reactions")
    private Comment comment;
    
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

