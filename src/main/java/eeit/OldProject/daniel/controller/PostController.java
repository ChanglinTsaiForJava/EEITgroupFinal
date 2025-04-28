package eeit.OldProject.daniel.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

    @PostMapping("/{id}/view")
    public ResponseEntity<Void> view(@PathVariable Long id) {
    	postService.incrementViewCount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<Void> share(@PathVariable Long id) {
    	postService.incrementShareCount(id);
        return ResponseEntity.noContent().build();
    }

	@GetMapping("/user/{uid}")
	public Page<Post> listByUser(@PathVariable Long uid, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return postService.getPostsByUser(uid, page, size);
	}

	@GetMapping
	public List<Post> getAll() {
		return postService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Post getById = postService.getById(id);
		if (getById != null) {
			return ResponseEntity.ok(getById);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Post post) {
		Post create = postService.create(post);
		if (create != null) {
			URI uri = URI.create("/api/posts/" + post.getPostId());
			return ResponseEntity.created(uri).body(create);
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody Post post) {
		if (id != null) {
			post.setPostId(id);
			Post modify = postService.modify(post);
			if (modify != null) {
				return ResponseEntity.ok(modify);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		if (postService.remove(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}