package eeit.OldProject.daniel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.repository.CommentRepository;

@SpringBootTest
class CommentServiceTest {

	@Autowired
	private PostService postService;
	
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepo;
    
    @Test
    void testSaveAndFindByPostId() {
    	// 清除資料
    	commentRepo.deleteAll();
    	
    	// 模擬建立 Post, Comment
    	Post post = new Post();
    	post.setContent("This is a post");
    	post.setCreatedAt(LocalDateTime.now());
    	Post savedPost = postService.create(post);
    	
    	Comment comment = new Comment();
    	comment.setContent("This is a comment.");
    	comment.setCreatedAt(LocalDateTime.now());
    	comment.setPost(savedPost);
    	
    	Comment savedComment = commentService.save(comment);
    	System.out.println("Saved Comment: " + savedComment);
    	
        List<Comment> comments = commentService.findByPostId(savedComment.getPost().getPostId());
        comments.forEach(System.out::println);
    }
}
