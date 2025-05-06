//package eeit.OldProject.daniel.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import eeit.OldProject.daniel.dto.PostRequest;
//import eeit.OldProject.daniel.entity.post.Post;
//import eeit.OldProject.daniel.entity.post.category.PostCategory;
//import eeit.OldProject.daniel.entity.post.tag.Tag;
//import eeit.OldProject.daniel.entity.post.topic.PostTopic;
//import eeit.OldProject.daniel.repository.post.PostRepository;
//import eeit.OldProject.daniel.repository.post.category.PostCategoryRepository;
//import eeit.OldProject.daniel.repository.post.tag.TagRepository;
//import eeit.OldProject.daniel.repository.post.topic.PostTopicRepository;
//import eeit.OldProject.steve.Entity.User;
//import eeit.OldProject.steve.Repository.UserRepository;
//
//@SpringBootTest
//class PostServiceTest {
//
//	@Autowired private PostService postService;
//	@Autowired private PostRepository postRepo;
//	@Autowired private UserRepository userRepo;
//	@Autowired private PostCategoryRepository postCategoryRepo;
//	@Autowired private PostTopicRepository postTopicRepo;
//	@Autowired private TagRepository tagRepo;
//	
//	private User user;
//	private PostCategory initCategory1;
//	private PostCategory initCategory2;
//	private PostTopic initTopic1;
//	private PostTopic initTopic2;
//	private Tag initTag1;
//	private Tag initTag2;
//	
//	@BeforeEach
//	void init() {
//		user = userRepo.save(new User(
//			null, "test", "test", "test", "test", "test", "test", "test", "test", 
//			LocalDateTime.now(), "test", 0L, "test", "test", "test", null)
//		);
//		initCategory1 = postCategoryRepo.save(PostCategory.builder().postCategory("Happy").build());
//		initCategory2 = postCategoryRepo.save(PostCategory.builder().postCategory("Angry").build());
//		initTopic1 = postTopicRepo.save(PostTopic.builder().topic("Full").build());
//		initTopic2 = postTopicRepo.save(PostTopic.builder().topic("Hungry").build());
//		initTag1 = tagRepo.save(Tag.builder().tagName("20250504").build());
//		initTag2 = tagRepo.save(Tag.builder().tagName("20250505").build());
//	}
//	
//	@Test
//	@Transactional
//	@Commit
//	void testcreateOrUpdate() {
//		// Create
//		PostRequest createPostRequest = PostRequest.builder()
//										.title("test title")
//										.content("test content")
//										.visibility((byte)0)
//										.status((byte)0)
//										.userId(user.getUserId())
//										.categoryIds(List.of(initCategory1.getPostCategoryId(), initCategory2.getPostCategoryId()))
//										.topicIds(List.of(initTopic1.getPostTopicId(), initTopic2.getPostTopicId()))
//										.build();
//		
//		Post created = postService.createOrUpdate(createPostRequest, null);
//		System.out.println("created="+created);
//		
//		// Update
//		PostRequest updatePostRequest = PostRequest.builder()
//										.title("update title")
//										.content("update content")
//										.visibility((byte)1)
//										.status((byte)1)
//										.userId(user.getUserId())
//										.categoryIds(List.of(initCategory1.getPostCategoryId()))
//										.topicIds(List.of(initTopic1.getPostTopicId()))
//										.build();
//		Post updated = postService.createOrUpdate(updatePostRequest, created.getPostId());
//		System.out.println("updated="+updated);
//	}
//	
////	@Test
//	@Transactional
//	void testSaveAndFindAll() {
//		// 建立資料
//		Post post = new Post();
//		post.setTitle("Test Post");
//		post.setContent("This is a test post.");
//		post.setCreatedAt(LocalDateTime.now());
//
//		Post savedPost = postService.create(post);
//		System.out.println("Saved Post: " + savedPost);
//
//		List<Post> posts = postService.getAll();
//		posts.forEach(System.out::println);
//	}
//
////    @Test
//	void testDelete() {
//		// 建立資料
//		Post post = new Post();
//		post.setTitle("Post to Delete");
//		post.setContent("This post will be deleted.");
//		post.setCreatedAt(LocalDateTime.now());
//
//		Post savedPost = postService.create(post);
//		Long postId = savedPost.getPostId();
//
//		// 刪除資料
//		postService.remove(postId);
//		boolean exists = postRepo.existsById(postId);
//		System.out.println("exists=" + exists);
//	}
//}