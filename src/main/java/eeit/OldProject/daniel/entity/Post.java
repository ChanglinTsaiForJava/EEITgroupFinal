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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "comments")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PostId")
    private Long postId;
    
    @Column(name="Title")
    private String title;

    @Lob
    @Column(name="Content", columnDefinition = "LONGTEXT")
    private String content;
    
    @Column(name="CreatedAt")
    private LocalDateTime createdAt;
    
    @Column(name="ModifiedAt")
    private LocalDateTime modifiedAt;
    
    @Column(name="Visibility")
    private Byte visibility;
    
    @Column(name="Status")
    private Byte status;
    
    @Column(name="Views")
    private Long views;
    
    @Column(name="Share")
    private Long share;
    
    @Column(name="UserId")
    private Long userId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("post")
    private List<Comment> comments;

}