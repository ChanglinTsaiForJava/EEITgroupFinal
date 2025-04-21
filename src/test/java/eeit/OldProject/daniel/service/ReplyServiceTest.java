package eeit.OldProject.daniel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.entity.Reply;
import eeit.OldProject.daniel.repository.CommentRepository;
import eeit.OldProject.daniel.repository.ReplyRepository;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Test
    void testSaveAndFindByCommentId() {
        // 清除資料
        replyRepo.deleteAll();
        commentRepo.deleteAll();

        // 建立 Comment
        Comment comment = new Comment();
        comment.setContent("This is a comment.");
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepo.save(comment);

        // 建立 Reply
        Reply reply = new Reply();
        reply.setContent("This is a reply.");
        reply.setCreatedAt(LocalDateTime.now());
        reply.setComment(savedComment);

        Reply savedReply = replyService.save(reply);
        System.out.println("Saved Reply: " + savedReply);

        List<Reply> replies = replyService.findByCommentId(savedComment.getCommentId());
        replies.forEach(System.out::println);
    }
}

