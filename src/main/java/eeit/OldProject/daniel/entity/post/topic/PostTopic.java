package eeit.OldProject.daniel.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString(exclude = {"classifiers"})
@Entity
@Table(name = "post_topic", schema = "final")
public class PostTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostTopicId")
    private Long postTopicId;

    @Column(name = "Topic", length = 20)
    private String topic;

    @OneToMany(mappedBy = "postTopic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("postTopic")
    private List<PostTopicClassifier> classifiers;
}

