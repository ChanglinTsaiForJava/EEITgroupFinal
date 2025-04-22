package eeit.OldProject.daniel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.entity.Reply;

@SpringBootTest
class ReplyRepositoryTest {

	@Autowired
	private ReplyRepository replyRepo;
	@Autowired
	private CommentRepository commentRepo;
	
	private Comment defaultCommen;

	@BeforeEach
	void setup() {
		Comment c = new Comment();
		c.setContent("預設評論");
		c.setCreatedAt(LocalDateTime.now());
		defaultCommen = commentRepo.save(c);
		System.out.println("defaultCommen="+defaultCommen);
	}
	
	@Test
	void testFindByCommentCommentId() {
		Reply reply1 = new Reply();
		reply1.setContent("留言1");
		reply1.setCreatedAt(LocalDateTime.now());
		reply1.setComment(defaultCommen);
		Reply savedRelply1 = replyRepo.save(reply1);
		System.out.println("savedRelply1=" + savedRelply1);

		Reply reply2 = new Reply();
		reply2.setContent("留言2");
		reply2.setCreatedAt(LocalDateTime.now());
		reply2.setComment(defaultCommen);
		Reply savedRelply2 = replyRepo.save(reply2);
		System.out.println("savedRelply2=" + savedRelply2);

		List<Reply> list = replyRepo.findByCommentCommentId(defaultCommen.getCommentId());
		list.stream().forEach(System.out::println);
	}

}