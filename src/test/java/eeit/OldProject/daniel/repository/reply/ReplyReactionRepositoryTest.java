package eeit.OldProject.daniel.repository.reply;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.reply.Reply;
import eeit.OldProject.daniel.entity.reply.ReplyReaction;
import eeit.OldProject.daniel.repository.reply.ReplyReactionRepository;
import eeit.OldProject.daniel.repository.reply.ReplyRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@SpringBootTest
class ReplyReactionRepositoryTest {

    @Autowired private ReplyReactionRepository repo;
    @Autowired private ReplyRepository replyRepo;
    @Autowired private UserRepository userRepo;

    @Test
    void saveAndCount() {
        Reply reply = replyRepo.save(Reply.builder().content("R").build());
        User user = new User();
        user.setUserName("U");
        userRepo.save(user);

        repo.save(ReplyReaction.builder()
            .reply(reply).user(user)
            .replyReaction((byte)1)
            .createdAt(LocalDateTime.now())
            .build());

        long count = repo.countByReplyReplyIdAndReplyReaction(reply.getReplyId(), (byte)1);
        System.out.println("count="+count);
    }
}
