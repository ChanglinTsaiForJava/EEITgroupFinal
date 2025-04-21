package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ReplyId")
    private Long replyId;

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
    @JoinColumn(name = "CommentId")
    @JsonIgnoreProperties("replies")
    private Comment comment;

}
