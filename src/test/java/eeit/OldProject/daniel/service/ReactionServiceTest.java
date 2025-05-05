package eeit.OldProject.daniel.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.reply.Reply;
import eeit.OldProject.daniel.repository.comment.CommentRepository;
import eeit.OldProject.daniel.repository.post.PostRepository;
import eeit.OldProject.daniel.repository.reply.ReplyRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@SpringBootTest
class ReactionServiceTest {

    @Autowired private ReactionService svc;
    @Autowired private PostRepository postRepo;
    @Autowired private CommentRepository commentRepo;
    @Autowired private ReplyRepository replyRepo;
    @Autowired private UserRepository userRepo;

    @Test
    void togglePostReaction() {
        User u = new User();
        u.setUserName("U");
        userRepo.save(u);
        
        Post p = postRepo.save(Post.builder().title("T").content("C").build());

        // 第一次按讚，count=1
        long c1 = svc.togglePostReaction(p.getPostId(), u.getUserId(), (byte)1);
        System.out.println("c1="+c1);
        
        // 再次按讚(收回)，count=0
        long c2 = svc.togglePostReaction(p.getPostId(), u.getUserId(), (byte)1);
        System.out.println("c2="+c2);
    }

    @Test
    void toggleCommentReaction() {
        User u = new User();
        u.setUserName("U");
        userRepo.save(u);
        
        Comment c = commentRepo.save(Comment.builder().content("C").build());

        long c1 = svc.toggleCommentReaction(c.getCommentId(), u.getUserId(), (byte)1);
        System.out.println("c1="+c1);

        long c2 = svc.toggleCommentReaction(c.getCommentId(), u.getUserId(), (byte)1);
        System.out.println("c2="+c2);
    }

    @Test
    void toggleReplyReaction() {
        User u = new User();
        u.setUserName("U");
        userRepo.save(u);
        
        Reply r = replyRepo.save(Reply.builder().content("R").build());

        long c1 = svc.toggleReplyReaction(r.getReplyId(), u.getUserId(), (byte)1);
        System.out.println("c1="+c1);

        long c2 = svc.toggleReplyReaction(r.getReplyId(), u.getUserId(), (byte)1);
        System.out.println("c2="+c2);
    }
}