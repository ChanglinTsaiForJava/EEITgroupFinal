package eeit.OldProject.daniel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.dto.PostFilter;
import eeit.OldProject.daniel.dto.PostRequest;
import eeit.OldProject.daniel.dto.TagDto;
import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.tag.PostTag;
import eeit.OldProject.daniel.entity.post.tag.Tag;
import eeit.OldProject.daniel.repository.post.PostRepository;
import eeit.OldProject.daniel.repository.post.tag.TagRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PostService {

	@Autowired private PostRepository postRepo;
	@Autowired private UserRepository userRepo;
	@Autowired private TagRepository tagRepo;
	@Autowired private PostCategoryService categoryService;
	@Autowired private PostTopicService topicService;

	public Post createOrUpdate(PostRequest postRequest, Long postId) {
		Post post = (postId == null) ? new Post()
				: postRepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

		User author = userRepo.findById(postRequest.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));

		post.setTitle(postRequest.getTitle());
		post.setContent(postRequest.getContent());
		post.setVisibility(postRequest.getVisibility());
		post.setStatus(postRequest.getStatus());
		post.setUser(author);

		List<Long> catIds = Optional.ofNullable(postRequest.getCategoryIds()).orElse(Collections.emptyList());
		List<Long> topIds = Optional.ofNullable(postRequest.getTopicIds()).orElse(Collections.emptyList());

		categoryService.syncPostCategories(post, catIds);
		topicService.syncPostTopics(post, topIds);

        // 以 tagName 撈出所有已存在的 Tag
        List<String> tagNames = Optional.ofNullable(postRequest.getTags())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(TagDto::getTagName)
                                .distinct()
                                .collect(Collectors.toList());
        // 批次查詢：findAllByTagNameIn(Collection<String>)
        List<Tag> existing = tagRepo.findAllByTagNameIn(tagNames);
        Map<String, Tag> nameToTag = existing.stream()
            .collect(Collectors.toMap(Tag::getTagName, Function.identity()));

        // 處理每個 TagDto：已有就拿它，沒有就建立
        List<PostTag> newPostTags = new ArrayList<>();
        for (TagDto t : Optional.ofNullable(postRequest.getTags()).orElse(Collections.emptyList())) {
            Tag tag = nameToTag.get(t.getTagName());
            if (tag == null) {
                // 不在 map，新增
                tag = tagRepo.save(Tag.builder()
                                     .tagName(t.getTagName())
                                     .build());
                nameToTag.put(tag.getTagName(), tag);
            }
            newPostTags.add(PostTag.builder()
                                   .post(post)
                                   .tag(tag)
                                   .build());
        }

        // 把舊關聯 clear，再放入新關聯
        post.getPostTags().clear();
        post.getPostTags().addAll(newPostTags);
		
		return postRepo.save(post);
	}

	public List<Post> findPosts(PostFilter filter) {
		return postRepo.searchPosts(filter);
	}

	// 觀看次數 +1
	public void incrementViewCount(Long postId) {
		postRepo.findById(postId).ifPresent(post -> {
			post.setViews(post.getViews() == null ? 1L : post.getViews() + 1);
			// 由於是托管實體，只要在事務內修改即可自動 flush
		});
	}

	// 分享次數 +1
	public Long incrementShareCount(Long postId) {
	    return postRepo.findById(postId)
	        .map(post -> {
	            Long newCount = post.getShare() == null ? 1L : post.getShare() + 1;
	            post.setShare(newCount);
	            postRepo.save(post);              // 記得存回資料庫
	            return newCount;
	        })
	        .orElseThrow(() -> new EntityNotFoundException("Post not found"));
	}

	public Page<Post> getPostsByUser(Long userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return postRepo.findByUser_UserId(userId, pageable);
	}

	public List<Post> getAll() {
		if (postRepo.findAll() != null) {
			return postRepo.findAll();
		}
		return null;
	}

	public Post getById(Long id) {
		if (id != null && postRepo.existsById(id)) {
			Optional<Post> findById = postRepo.findById(id);
			return findById.get();
		}
		return null;
	}

	public Post create(Post post) {
		if (post != null) {
			User user = post.getUser();
			if (user != null && user.getUserId() != null) {
				Optional<User> userFound = userRepo.findById(user.getUserId());
				if (userFound.isPresent()) {
					post.setUser(userFound.get());
					return postRepo.save(post);
				}
			}
		}
		return null;
	}

	public Post modify(Post post) {
		if (post != null && post.getPostId() != null) {
			Optional<Post> postFindById = postRepo.findById(post.getPostId());
			if (postFindById.isPresent()) {
				Post saved = postFindById.get();
				post.setCreatedAt(saved.getCreatedAt());
				post.setUser(saved.getUser());
				post.setComments(saved.getComments());
				post.setViews(saved.getViews());
				post.setShare(saved.getShare());
				return postRepo.save(post);
			}
		}
		return null;
	}

	public boolean remove(Long id) {
		if (id != null) {
			if (postRepo.existsById(id)) {
				try {
					postRepo.deleteById(id);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}