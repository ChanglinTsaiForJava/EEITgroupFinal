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
@ToString(exclude = {"savedPost","savedCollection"})
@Entity
@Table(name = "saved_post_collector", schema = "final")
public class SavedPostCollector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SavedPostCollectorId")
    private Long savedPostCollectorId;

    @Column(name = "SavedPostId")
    private Long savedPostId;

    @Column(name = "SavedCollectionId")
    private Long savedCollectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SavedPostId", insertable = false, updatable = false)
    @JsonIgnoreProperties("collectors")
    private SavedPost savedPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SavedCollectionId", insertable = false, updatable = false)
    @JsonIgnoreProperties("savedPosts")
    private SavedCollection savedCollection;
}