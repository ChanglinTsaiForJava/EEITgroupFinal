package eeit.OldProject.daniel.entity.post.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.daniel.entity.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = {"postCategory","post"})
@Entity
@Table(name = "post_category_classifier", schema = "final")
public class PostCategoryClassifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostCategoryClassifierId")
    private Long postCategoryClassifierId;

    @ManyToOne
    @JoinColumn(name = "PostCategoryId")
    @JsonIgnoreProperties("classifiers")
    private PostCategory postCategory;

    @ManyToOne
    @JoinColumn(name = "PostId")
    @JsonIgnoreProperties("classifiers")
    private Post post;
}

