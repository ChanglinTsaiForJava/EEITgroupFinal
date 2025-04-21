package eeit.OldProject.daniel.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.entity.PostImage;
import eeit.OldProject.daniel.repository.PostRepository;

@SpringBootTest
class PostImageServiceTest {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private PostImageService imageSvc;

	@Test
	@Transactional
	@Commit
	@Rollback(false)
	void testSaveAndFindByPostId() throws Exception {
		// 建置 Post
		Post post = new Post();
		post.setTitle("Service Test");
		post.setContent("Service content");
		post.setCreatedAt(LocalDateTime.now());
		Post saved = postRepo.save(post);

		// 讀取本機圖片
		Path imgPath = Path.of("C:/CarePlus/images/pikachu.png");
		byte[] data = Files.readAllBytes(imgPath);

		// 使用 Service 儲存
		PostImage savedImg = imageSvc.save(data, saved.getPostId());
		System.out.println("savedImg="+savedImg);

		// 找回
		List<PostImage> list = imageSvc.findByPostId(saved.getPostId());
		list.stream().forEach(System.out::println);
	}
}
