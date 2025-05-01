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
@ToString(exclude = {"user","post"})
@Entity
@Table(name = "post_reaction", schema = "final")
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostReactionId")
    private Long postReactionId;

    @Column(name = "PostReaction")
    private Byte postReaction;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonIgnoreProperties("postReactions")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PostId")
    @JsonIgnoreProperties("reactions")
    private Post post;
    
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
