package eeit.OldProject.daniel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentCommentId(Long commentId);
}

