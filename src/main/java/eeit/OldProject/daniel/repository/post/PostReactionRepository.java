package eeit.OldProject.daniel.repository.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.post.PostReaction;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
	Optional<PostReaction> findByPostPostIdAndUserUserId(Long postId, Long userId);

	long countByPostPostIdAndPostReaction(Long postId, Byte reaction);
}
