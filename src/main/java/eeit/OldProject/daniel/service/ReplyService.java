package eeit.OldProject.daniel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.entity.reply.Reply;
import eeit.OldProject.daniel.repository.comment.CommentRepository;
import eeit.OldProject.daniel.repository.reply.ReplyRepository;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@Service
@Transactional
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CommentRepository commentRepo;

	public List<Reply> findByCommentId(Long commentId) {
		return replyRepo.findByCommentCommentId(commentId);
	}

	public Reply create(Reply reply) {
		if (reply!=null) {
			User user = reply.getUser();
			Comment comment = reply.getComment();
			if (user!=null && user.getUserId()!=null && comment!=null && comment.getCommentId()!=null) {
				Optional<User> userFound = userRepo.findById(user.getUserId());
				Optional<Comment> commentFound = commentRepo.findById(comment.getCommentId());
				if (userFound.isPresent() && commentFound.isPresent()) {
					reply.setUser(userFound.get());
					reply.setComment(commentFound.get());
					return replyRepo.save(reply);
				}
			}
		}
		return null;
	}
	
	public Reply modify(Reply reply) {
		if (reply!=null && reply.getReplyId()!=null) {
			Optional<Reply> replyFindById = replyRepo.findById(reply.getReplyId());
			if (replyFindById.isPresent()) {
				Reply saved = replyFindById.get();
				reply.setCreatedAt(saved.getCreatedAt());
				reply.setUser(saved.getUser());
				reply.setComment(saved.getComment());
				return replyRepo.save(reply);
			}
		}
		return null;
	}

	public void remove(Long id) {
		replyRepo.deleteById(id);
	}
}