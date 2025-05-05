package eeit.OldProject.daniel.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.category.PostCategory;
import eeit.OldProject.daniel.entity.post.category.PostCategoryClassifier;
import eeit.OldProject.daniel.repository.post.category.PostCategoryClassifierRepository;
import eeit.OldProject.daniel.repository.post.category.PostCategoryRepository;
import jakarta.persistence.EntityNotFoundException;

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

	// 指派貼文多個種類
	public List<PostCategoryClassifier> assignCategories(Long postId, List<Long> categoryIds) {
		List<PostCategoryClassifier> classifiers = categoryIds.stream()
				.map(categoryId -> PostCategoryClassifier.builder().post(new Post() {
					{
						setPostId(postId);
					}
				}).postCategory(new PostCategory() {
					{
						setPostCategoryId(categoryId);
					}
				}).build()).collect(Collectors.toList());
		return classifierRepo.saveAll(classifiers);
	}
	
	// 指派貼文至種類
	public PostCategoryClassifier assignCategory(Long postId, Long categoryId) {
		PostCategoryClassifier pc = PostCategoryClassifier.builder().postCategory(new PostCategory() {
			{
				setPostCategoryId(categoryId);
			}
		}).post(new Post() {
			{
				setPostId(postId);
			}
		}).build();
		return classifierRepo.save(pc);
	}

	// 移除貼文與種類對應
	public void removeAssignment(Long classifierId) {
		classifierRepo.deleteById(classifierId);
	}
	
	// 同步貼文種類
	public void syncPostCategories(Post post, List<Long> newCatIds) {
	    // 取出現有 classifier (含 categoryId)
		Map<Long, PostCategoryClassifier> oldMap = post.getPostCategoryClassifiers()
			.stream()
			.collect(Collectors.toMap(
				c -> c.getPostCategory().getPostCategoryId(), 
				Function.identity()
			));
		
	    // 決定要新增／刪除哪些
		Set<Long> toAdd = new HashSet<>(newCatIds);
		toAdd.removeAll(oldMap.keySet());
		
		Set<Long> toRemove = new HashSet<>(oldMap.keySet());
		toRemove.removeAll(newCatIds);
		
	    // 刪除
		toRemove.forEach(catId -> {
			post.removeCategory(oldMap.get(catId));
		});
		
		// 新增
		toAdd.forEach(catId -> {
			PostCategory postCategory = categoryRepo.findById(catId)
										.orElseThrow(() -> new EntityNotFoundException("Cat not found"));
			post.addCategory(postCategory);
		});
	}
}
