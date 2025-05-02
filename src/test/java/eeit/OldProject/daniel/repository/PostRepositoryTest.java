package eeit.OldProject.daniel.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.post.Post;

@SpringBootTest
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepo;

	private Post defaultPost;
	
	@BeforeEach
	void setup() {
		Post p = new Post();
		p.setTitle("預設貼文");
		p.setContent("預設內容");
		defaultPost = postRepo.save(p);
	}	
	
	@Test
	void testCount() {
		long count = postRepo.count();
		System.out.println("count=" + count);
	}
	
//	@Test
	void testExists() {
		boolean exists = postRepo.existsById(defaultPost.getPostId());
		System.out.println("exists=" + exists);
	}
	
	@Test
	void testInsert() {
		Post p = new Post();
		p.setTitle("測試貼文");
		p.setContent("Content...");

		Post saved = postRepo.save(p);
		System.out.println("saved=" + saved);
	}

//  @Test
	void testUpdate() {
		Post p = new Post();
		p.setTitle("修改貼文");
		p.setContent("Content...");
		p.setPostId(defaultPost.getPostId());

		Post updated = postRepo.save(p);
		System.out.println("updated=" + updated);
	}

//	@Test
	void testDelete() {
		postRepo.deleteById(defaultPost.getPostId());
		boolean exists = postRepo.existsById(defaultPost.getPostId());
		System.out.println("exists=" + exists);
	}

//	@Test
	void testFindById() {
		Optional<Post> findById = postRepo.findById(36L);
		Post get = findById.get();
		System.out.println("get=" + get);
	}

//	@Test
	void testFindAll() {
		List<Post> findAll = postRepo.findAll();
		findAll.stream().forEach(System.out::println);
	}

}