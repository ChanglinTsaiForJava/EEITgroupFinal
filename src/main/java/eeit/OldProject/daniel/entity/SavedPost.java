package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"user","post","collectors"})
@Entity
@Table(name = "saved_post", schema = "final")
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SavedPostId")
    private Long savedPostId;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "PostId")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    @JsonIgnoreProperties("savedPosts")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PostId", insertable = false, updatable = false)
    @JsonIgnoreProperties("savedPosts")
    private Post post;

    @OneToMany(mappedBy = "savedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("savedPost")
    private List<SavedPostCollector> collectors;
}