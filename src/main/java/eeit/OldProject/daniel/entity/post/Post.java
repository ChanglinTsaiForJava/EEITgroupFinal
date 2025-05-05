package eeit.OldProject.daniel.entity.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.entity.post.category.PostCategory;
import eeit.OldProject.daniel.entity.post.category.PostCategoryClassifier;
import eeit.OldProject.daniel.entity.post.tag.PostTag;
import eeit.OldProject.daniel.entity.post.tag.Tag;
import eeit.OldProject.daniel.entity.post.topic.PostTopic;
import eeit.OldProject.daniel.entity.post.topic.PostTopicClassifier;
import eeit.OldProject.steve.Entity.User;
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
@ToString(exclude = { "user", "comments" })
@Entity
@Table(name = "post", schema = "final")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PostId")
	private Long postId;

	@Column(name = "Title", length = 100)
	private String title;

	@Lob
	@Column(name = "Content", columnDefinition = "LONGTEXT")
	private String content;

	@Column(name = "CreatedAt")
	private LocalDateTime createdAt;

	@Column(name = "ModifiedAt")
	private LocalDateTime modifiedAt;

	@Column(name = "Visibility")
	private Byte visibility;

	@Column(name = "Status")
	private Byte status;

	@Column(name = "Views")
	private Long views;

	@Column(name = "Share")
	private Long share;

	@ManyToOne
	@JoinColumn(name = "UserId")
	@JsonIgnoreProperties("posts")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("post")
	private List<Comment> comments;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("post")
	private List<PostImage> images;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("post")
	private List<PostReaction> reactions;

	@Builder.Default
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("post")
	private List<PostCategoryClassifier> postCategoryClassifiers = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("post")
	private List<PostTopicClassifier> postTopicClassifiers = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("post")
	private List<PostTag> postTags = new ArrayList<>();

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.modifiedAt = now;
		this.views = 0L;
		this.share = 0L;
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedAt = LocalDateTime.now();
	}

	public void addCategory(PostCategory postCategory) {
		postCategoryClassifiers.add(PostCategoryClassifier.builder().post(this).postCategory(postCategory).build());
	}

	public void removeCategory(PostCategoryClassifier postCategoryClassifier) {
		postCategoryClassifiers.remove(postCategoryClassifier);
	}

	public void addTopic(PostTopic postTopic) {
		postTopicClassifiers.add(PostTopicClassifier.builder().post(this).postTopic(postTopic).build());
	}

	public void removeTopic(PostTopicClassifier postTopicClassifier) {
		postTopicClassifiers.remove(postTopicClassifier);
	}

	public void addTag(Tag tag) {
		PostTag pt = PostTag.builder().post(this).tag(tag).build();
		postTags.add(pt);
		tag.getPostTags().add(pt);
	}

    public void removeTag(PostTag postTag) {
        postTags.remove(postTag);
        postTag.getTag().getPostTags().remove(postTag);
        postTag.setPost(null);
        postTag.setTag(null);
    }
}
