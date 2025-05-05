package eeit.OldProject.daniel.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.daniel.entity.comment.Comment;
import eeit.OldProject.daniel.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public List<Comment> byPost(@RequestParam Long postId) {
		return commentService.findByPostId(postId);
	}

	@PostMapping
	public ResponseEntity<Comment> create(@RequestBody Comment comment) {
		Comment create = commentService.create(comment);
		if (create != null) {
			URI uri = URI.create("/api/comments" + create.getCommentId());
			return ResponseEntity.created(uri).body(create);
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Comment> modify(@PathVariable Long id, @RequestBody Comment comment) {
		if (id != null) {
			comment.setCommentId(id);
			Comment modify = commentService.modify(comment);
			if (modify!=null) {
				return ResponseEntity.ok(modify);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		commentService.remove(id);
		return ResponseEntity.noContent().build();
	}
}