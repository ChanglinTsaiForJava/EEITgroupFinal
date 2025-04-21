package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "replies")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CommentId")
    private Long commentId;

    @Lob
    @Column(name="Content", columnDefinition = "LONGTEXT")
    private String content;
    
    @Column(name="CreatedAt")
    private LocalDateTime createdAt;
    
    @Column(name="ModifiedAt")
    private LocalDateTime modifiedAt;
    
    @Column(name="Status")
    private Byte status;
    
    @Column(name="UserId")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "PostId")
    @JsonIgnoreProperties("comments")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("comment")
    private List<Reply> replies;

}