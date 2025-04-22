package eeit.OldProject.daniel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.repository.PostRepository;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepo;

    @Test
    void testSaveAndFindAll() {
        // 清除資料
//        postRepo.deleteAll();

        // 建立資料
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postService.save(post);
        System.out.println("Saved Post: " + savedPost);

        List<Post> posts = postService.findAll();
        posts.forEach(System.out::println);
    }

//    @Test
    void testDelete() {
        // 建立資料
        Post post = new Post();
        post.setTitle("Post to Delete");
        post.setContent("This post will be deleted.");
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postService.save(post);
        Long postId = savedPost.getPostId();

        // 刪除資料
        postService.delete(postId);
        boolean exists = postRepo.existsById(postId);
        System.out.println("exists="+exists);
    }
}