package eeit.OldProject.daniel.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.entity.PostCategory;
import eeit.OldProject.daniel.entity.PostCategoryClassifier;
import eeit.OldProject.daniel.repository.PostRepository;

@SpringBootTest
public class PostCategoryServiceTest {
	
    @Autowired private PostCategoryService svc;
    
    @Autowired private PostRepository postRepo;

    @Test
    void testCategoryFlow() {
        // 準備資料：先新增一個貼文
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content");
        post = postRepo.save(post);
        System.out.println("Created Post: " + post);
    	
        // 新增種類
        PostCategory cat = new PostCategory();
        cat.setPostCategory("Tech");
        PostCategory saved = svc.createCategory(cat);
        System.out.println("Created Category: " + saved);

        // 列出所有種類
        List<PostCategory> list = svc.findAllCategories();
        System.out.println("All Categories: " + list);

        // 指派貼文到種類
        PostCategoryClassifier pc = svc.assignCategory(post.getPostId(), saved.getPostCategoryId());
        System.out.println("Assigned Classifier: " + pc);

        // 刪除指派
        svc.removeAssignment(pc.getPostCategoryClassifierId());
        System.out.println("Removed Classifier ID: " + pc.getPostCategoryClassifierId());
    }
}


