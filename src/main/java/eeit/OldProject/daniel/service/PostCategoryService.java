package eeit.OldProject.daniel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.PostCategory;
import eeit.OldProject.daniel.entity.post.PostCategoryClassifier;
import eeit.OldProject.daniel.repository.PostCategoryClassifierRepository;
import eeit.OldProject.daniel.repository.PostCategoryRepository;

@Service
@Transactional
public class PostCategoryService {
	
	@Autowired
    private PostCategoryRepository categoryRepo;
	@Autowired
    private PostCategoryClassifierRepository classifierRepo;

    // 取得所有種類
    public List<PostCategory> findAllCategories() {
        return categoryRepo.findAll();
    }

    // 新增種類
    public PostCategory createCategory(PostCategory category) {
        return categoryRepo.save(category);
    }

    // 指派貼文至種類
    public PostCategoryClassifier assignCategory(Long postId, Long categoryId) {
        PostCategoryClassifier pc = PostCategoryClassifier.builder()
            .postCategory(new PostCategory() {{ setPostCategoryId(categoryId); }})
            .post(new Post() {{ setPostId(postId); }})
            .build();
        return classifierRepo.save(pc);
    }

    // 移除貼文與種類對應
    public void removeAssignment(Long classifierId) {
        classifierRepo.deleteById(classifierId);
    }
}
