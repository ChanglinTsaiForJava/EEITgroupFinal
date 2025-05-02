package eeit.OldProject.daniel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.repository.PostRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@Service
@Transactional
public class PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;
	
    // 觀看次數 +1
    public void incrementViewCount(Long postId) {
        postRepo.findById(postId).ifPresent(post -> {
            post.setViews(post.getViews() == null ? 1L : post.getViews() + 1);
            // 由於是托管實體，只要在事務內修改即可自動 flush
        });
    }

    // 分享次數 +1
    public void incrementShareCount(Long postId) {
        postRepo.findById(postId).ifPresent(post -> {
            post.setShare(post.getShare() == null ? 1L : post.getShare() + 1);
        });
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