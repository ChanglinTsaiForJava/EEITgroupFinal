package eeit.OldProject.daniel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.entity.post.Post;

@SpringBootTest
class CommentRepositoryTest {

	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private PostRepository postRepo;

	private Post defaultPost;
	
	@BeforeEach
	void setup() {
		Post p = new Post();
		p.setTitle("預設貼文");
		p.setCreatedAt(LocalDateTime.now());
		defaultPost = postRepo.save(p);
		System.out.println("defaultPost="+defaultPost);
	}
	
	@Test
	void findByPostId() {
		Comment comment1 = new Comment();
		comment1.setContent("留言1");
		comment1.setCreatedAt(LocalDateTime.now());
		comment1.setPost(defaultPost);
		Comment savedComment1 = commentRepo.save(comment1);
		System.out.println("savedComment1="+savedComment1);

		Comment comment2 = new Comment();
		comment2.setContent("留言2");
		comment2.setCreatedAt(LocalDateTime.now());
		comment2.setPost(defaultPost);
		Comment savedComment2 = commentRepo.save(comment2);
		System.out.println("savedComment2="+savedComment2);

		List<Comment> list = commentRepo.findByPostPostId(defaultPost.getPostId());
		list.stream().forEach(System.out::println);
	}
}