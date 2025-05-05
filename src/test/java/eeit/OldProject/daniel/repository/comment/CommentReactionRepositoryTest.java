package eeit.OldProject.daniel.repository.comment;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.entity.comment.CommentReaction;
import eeit.OldProject.daniel.repository.comment.CommentReactionRepository;
import eeit.OldProject.daniel.repository.comment.CommentRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@SpringBootTest
class CommentReactionRepositoryTest {

    @Autowired private CommentReactionRepository repo;
    @Autowired private CommentRepository commentRepo;
    @Autowired private UserRepository userRepo;

    @Test
    void saveAndCount() {
        Comment comment = commentRepo.save(Comment.builder().content("C").build());
        User user = new User();
        user.setUserName("U");
        userRepo.save(user);

        repo.save(CommentReaction.builder()
            .comment(comment).user(user)
            .commentReaction((byte)1)
            .createdAt(LocalDateTime.now())
            .build());

        long count = repo.countByCommentCommentIdAndCommentReaction(comment.getCommentId(), (byte)1);
        System.out.println("count="+count);
    }
}
