package eeit.OldProject.daniel.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.category.PostCategory;
import eeit.OldProject.daniel.entity.post.category.PostCategoryClassifier;
import eeit.OldProject.daniel.repository.post.PostRepository;
import eeit.OldProject.steve.Entity.User;

@SpringBootTest
public class PostCategoryServiceTest {
	
    @Autowired private PostCategoryService postCategoryService;
    
    @Autowired private PostRepository postRepo;
    
    private User user;
    
    @BeforeEach
    void init() {
    	// 設定使用者
    	user = new User();
    	user.setUserId(3L);
    }

//    @Test
    void testCategoryFlow() {
        // 準備資料：先新增一個貼文
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content");
        post.setUser(user);
        post = postRepo.save(post);
        System.out.println("Created Post: " + post);
    	
        // 新增種類
        PostCategory cat = new PostCategory();
        cat.setPostCategory("Tech");
        PostCategory saved = postCategoryService.createCategory(cat);
        System.out.println("Created Category: " + saved);

        // 列出所有種類
        List<PostCategory> list = postCategoryService.findAllCategories();
        System.out.println("All Categories: " + list);

        // 指派貼文到種類
        PostCategoryClassifier pc = postCategoryService.assignCategory(post.getPostId(), saved.getPostCategoryId());
        System.out.println("Assigned Classifier: " + pc);

        // 刪除指派
        postCategoryService.removeAssignment(pc.getPostCategoryClassifierId());
        System.out.println("Removed Classifier ID: " + pc.getPostCategoryClassifierId());
    }
    
    @Test
    void testAssignCategories() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content");
        post.setUser(user);
        post = postRepo.save(post);
        System.out.println("Created Post: " + post);
        
        // 新增種類1
        PostCategory cat1 = new PostCategory();
        cat1.setPostCategory("Tech");
        PostCategory saved1 = postCategoryService.createCategory(cat1);
        System.out.println("Created Category: " + saved1);
        
        // 新增種類2
        PostCategory cat2 = new PostCategory();
        cat2.setPostCategory("Art");
        PostCategory saved2 = postCategoryService.createCategory(cat2);
        System.out.println("Created Category: " + saved2);
        
        // 增加後，列出原有種類
        List<PostCategory> creatList = postCategoryService.findAllCategories();
        System.out.println("All Categories: " + creatList);
        
        // 指派兩個分類給一個貼文
        List<PostCategoryClassifier> classifiers = postCategoryService.assignCategories(
        		post.getPostId(), 
        		List.of(saved1.getPostCategoryId(), saved2.getPostCategoryId())
        		);
        System.out.println("classifiers="+classifiers);
        
        // 刪除指派
        classifiers.stream().forEach(
        		classifier -> postCategoryService.removeAssignment(classifier.getPostCategoryClassifierId()));
        
        // 刪除後，列出所有種類
        List<PostCategory> removeList = postCategoryService.findAllCategories();
        System.out.println("All Categories: " + removeList);
    }
}


