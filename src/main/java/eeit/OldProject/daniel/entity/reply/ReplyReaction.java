package eeit.OldProject.daniel.entity;

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
@ToString(exclude = {"user","reply"})
@Entity
@Table(name = "reply_reaction", schema = "final")
public class ReplyReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReplyReactionId")
    private Long replyReactionId;

    @Column(name = "ReplyReaction")
    private Byte replyReaction;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonIgnoreProperties("replyReactions")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ReplyId")
    @JsonIgnoreProperties("reactions")
    private Reply reply;
    
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
