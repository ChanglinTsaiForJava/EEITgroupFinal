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

import eeit.OldProject.daniel.entity.Post;
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
		if (post != null && post.getPostId() != null && post.getUser() != null && post.getUser().getUserId() != null) {
			Optional<Post> postFindById = postRepo.findById(post.getPostId());
			Optional<User> userFindById = userRepo.findById(post.getUser().getUserId());
			if (postFindById.isPresent() && userFindById.isPresent()) {
				Post saved = postFindById.get();
				post.setCreatedAt(saved.getCreatedAt());
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