package eeit.OldProject.daniel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.repository.PostRepository;

@Service
@Transactional
public class PostService {
	
	@Autowired
	private PostRepository postRepo;

	public List<Post> findAll() {
		return postRepo.findAll();
	}

	public Post findById(Long id) {
		if (id!=null && postRepo.existsById(id)) {
			Optional<Post> findById = postRepo.findById(id);
			return findById.get();
		}
		return null;
	}

	public Post save(Post post) {
		return postRepo.save(post);
	}

	public void delete(Long id) {
		postRepo.deleteById(id);
	}

}