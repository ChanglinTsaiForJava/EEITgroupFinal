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
import eeit.OldProject.daniel.entity.post.tag.PostTag;
import eeit.OldProject.daniel.entity.post.tag.Tag;
import eeit.OldProject.daniel.repository.post.tag.PostTagRepository;
import eeit.OldProject.daniel.repository.post.tag.TagRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PostTagService {

	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private PostTagRepository postTagRepo;

	// 取得所有標籤
	public List<Tag> findAllTags() {
		return tagRepo.findAll();
	}

	// 新增標籤
	public Tag createTag(Tag tag) {
		return tagRepo.save(tag);
	}

	// 指派貼文多個標籤
	public List<PostTag> assignTags(Long postId, List<Long> tagIds) {
		List<PostTag> assignments = tagIds.stream().map(tagId -> PostTag.builder().post(new Post() {
			{
				setPostId(postId);
			}
		}).tag(new Tag() {
			{
				setTagId(tagId);
			}
		}).build()).collect(Collectors.toList());
		return postTagRepo.saveAll(assignments);
	}

	// 指派貼文至標籤
	public PostTag assignTag(Long postId, Long tagId) {
		PostTag pt = PostTag.builder().post(new Post() {
			{
				setPostId(postId);
			}
		}).tag(new Tag() {
			{
				setTagId(tagId);
			}
		}).build();
		return postTagRepo.save(pt);
	}

	// 移除貼文與標籤對應
	public void removeAssignment(Long id) {
		postTagRepo.deleteById(id);
	}

	// 同步貼文標籤
	public void syncPostTags(Post post, List<Long> newTagIds) {
		// 1. 現有關聯 Map<tagId, PostTag>
		Map<Long, PostTag> oldMap = post.getPostTags().stream()
				.collect(Collectors.toMap(pt -> pt.getTag().getTagId(), Function.identity()));

		// 2. 計算要新增／移除的 tagId 集合
		Set<Long> toAdd = new HashSet<>(newTagIds);
		Set<Long> toRemove = new HashSet<>(oldMap.keySet());
		toAdd.removeAll(oldMap.keySet());
		toRemove.removeAll(newTagIds);

		// 3. 刪除不再需要的關聯
		toRemove.forEach(id -> post.removeTag(oldMap.get(id)));

		// 4. 批次撈出所有要新增的 Tag 實體
		List<Tag> tagsToAdd = tagRepo.findAllById(toAdd);
		// 5. 檢查有無不存在的 id
		Set<Long> found = tagsToAdd.stream().map(Tag::getTagId).collect(Collectors.toSet());
		toAdd.stream().filter(id -> !found.contains(id)).findFirst().ifPresent(id -> {
			throw new EntityNotFoundException("Tag not found: " + id);
		});

		// 6. 新增新的關聯
		tagsToAdd.forEach(post::addTag);
	}
}