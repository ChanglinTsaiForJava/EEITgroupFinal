package eeit.OldProject.daniel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.daniel.entity.post.PostCategory;
import eeit.OldProject.daniel.entity.post.PostCategoryClassifier;
import eeit.OldProject.daniel.service.PostCategoryService;

@RestController
@RequestMapping("/api/categories")
public class PostCategoryController {
	
	@Autowired
    private PostCategoryService svc;

    @GetMapping
    public List<PostCategory> all() {
        return svc.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<PostCategory> create(@RequestBody PostCategory c) {
        return ResponseEntity.ok(svc.createCategory(c));
    }

    @PostMapping("/assign")
    public ResponseEntity<PostCategoryClassifier> assign(
            @RequestParam Long postId,
            @RequestParam Long categoryId) {
        return ResponseEntity.ok(svc.assignCategory(postId, categoryId));
    }

    @DeleteMapping("/assign/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        svc.removeAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
