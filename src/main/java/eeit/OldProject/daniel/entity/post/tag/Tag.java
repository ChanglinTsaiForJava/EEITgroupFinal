package eeit.OldProject.daniel.entity.post.tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString(exclude = {"postTags"})
@Entity
@Table(name = "tag", schema = "final")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagId")
    private Long tagId;

    @Column(name = "TagName", length = 80)
    private String tagName;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("tag")
    private List<PostTag> postTags = new ArrayList<>();;
    
    @PrePersist
    protected void onCreate() {
      LocalDateTime now = LocalDateTime.now();
      this.createdAt = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
    	LocalDateTime now = LocalDateTime.now();
    	this.createdAt = now;
    }
}
