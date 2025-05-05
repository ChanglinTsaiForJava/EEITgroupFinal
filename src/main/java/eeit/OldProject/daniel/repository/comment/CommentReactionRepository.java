package eeit.OldProject.daniel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.comment.CommentReaction;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
  Optional<CommentReaction> findByCommentCommentIdAndUserUserId(Long commentId, Long userId);
  long countByCommentCommentIdAndCommentReaction(Long commentId, Byte reaction);
}