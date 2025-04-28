package eeit.OldProject.daniel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.repository.CommentRepository;
import eeit.OldProject.daniel.repository.PostRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PostRepository postRepoy;

	public List<Comment> findByPostId(Long postId) {
		return commentRepo.findByPostPostId(postId);
	}

	public Comment create(Comment comment) {
		if (comment != null) {
			User user = comment.getUser();
			Post post = comment.getPost();
			if (user != null && user.getUserId() != null && post != null && post.getPostId() != null) {
				Optional<User> userFound = userRepo.findById(user.getUserId());
				Optional<Post> postFound = postRepoy.findById(post.getPostId());
				if (userFound.isPresent() && postFound.isPresent()) {
					comment.setUser(userFound.get());
					comment.setPost(postFound.get());
					return commentRepo.save(comment);
				}
			}
		}
		return null;
	}

	public Comment modify(Comment comment) {
		if (comment != null && comment.getCommentId() != null) {
			Optional<Comment> commentFindById = commentRepo.findById(comment.getCommentId());
			if (commentFindById.isPresent()) {
				Comment saved = commentFindById.get();
				comment.setCreatedAt(saved.getCreatedAt());
				comment.setUser(saved.getUser());
				comment.setPost(saved.getPost());
				comment.setReplies(saved.getReplies());
				return commentRepo.save(comment);
			}
		}
		return null;
	}

	public void remove(Long id) {
		commentRepo.deleteById(id);
	}
}