package eeit.OldProject.daniel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.reply.ReplyReaction;

public interface ReplyReactionRepository extends JpaRepository<ReplyReaction, Long> {
  Optional<ReplyReaction> findByReplyReplyIdAndUserUserId(Long replyId, Long userId);
  long countByReplyReplyIdAndReplyReaction(Long replyId, Byte reaction);
}