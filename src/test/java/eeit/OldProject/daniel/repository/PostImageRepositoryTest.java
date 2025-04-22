package eeit.OldProject.daniel.repository;

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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
class PostImageRepositoryTest {
	
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private PostImageRepository imageRepo;

	@Test
	@Transactional
	@Commit
	@Rollback(false)
	void testSaveAndFindByPostId() throws Exception {
		// 建置 Post
		Post post = new Post();
		post.setTitle("Post image test");
		post.setContent("Test content");
		post.setCreatedAt(LocalDateTime.now());
		Post saved = postRepo.save(post);

		// 從本機讀入圖片檔案
		Path imgPath = Path.of("C:/CarePlus/images/bulbasaur.png");
		byte[] data = Files.readAllBytes(imgPath);

		// 儲存 PostImage
		PostImage img = new PostImage();
		img.setImageData(data);
		img.setUploadedAt(LocalDateTime.now());
		img.setPost(saved);
		imageRepo.save(img);

		// 驗證
		List<PostImage> list = imageRepo.findByPostPostId(saved.getPostId());
		list.stream().forEach(System.out::println);
	}

}