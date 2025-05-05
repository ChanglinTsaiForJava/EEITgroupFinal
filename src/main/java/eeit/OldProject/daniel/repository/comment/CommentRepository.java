package eeit.OldProject.daniel.repository.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostId(Long postId);
}
