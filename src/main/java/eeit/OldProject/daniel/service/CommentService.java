package eeit.OldProject.daniel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.repository.CommentRepository;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepo;


	public List<Comment> findByPostId(Long postId) {
		return commentRepo.findByPostPostId(postId);
	}

	public Comment save(Comment comment) {
		return commentRepo.save(comment);
	}

	public void delete(Long id) {
		commentRepo.deleteById(id);
	}
}