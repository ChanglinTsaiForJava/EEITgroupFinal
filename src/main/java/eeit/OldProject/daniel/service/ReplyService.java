package eeit.OldProject.daniel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Reply;
import eeit.OldProject.daniel.repository.ReplyRepository;

@Service
@Transactional
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepo;

	public List<Reply> findByCommentId(Long commentId) {
		return replyRepo.findByCommentCommentId(commentId);
	}

	public Reply save(Reply reply) {
		return replyRepo.save(reply);
	}

	public void delete(Long id) {
		replyRepo.deleteById(id);
	}
}