package eeit.OldProject.daniel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column(name = "PostCategoryId")
    private Long postCategoryId;

    @Column(name = "PostId")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PostCategoryId", insertable = false, updatable = false)
    @JsonIgnoreProperties("classifiers")
    private PostCategory postCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PostId", insertable = false, updatable = false)
    @JsonIgnoreProperties("classifiers")
    private Post post;
}
