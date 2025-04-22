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
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	public List<Post> all() {
		return postService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> one(@PathVariable Long id) {
		Post findById = postService.findById(id);
		if (findById != null) {
			return ResponseEntity.ok(findById);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Post> create(@RequestBody Post p) {
		Post save = postService.save(p);
		if (save != null) {
			URI uri = URI.create("/api/posts/"+p.getPostId());
			return ResponseEntity.created(uri).body(save);
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post p) {
		p.setPostId(id);
		return ResponseEntity.ok(postService.save(p));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.delete(id);
		return ResponseEntity.noContent().build();
	}
}