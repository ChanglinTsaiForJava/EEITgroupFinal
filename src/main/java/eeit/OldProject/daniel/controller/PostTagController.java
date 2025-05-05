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

import eeit.OldProject.daniel.dto.PostTagAssignBatchDto;
import eeit.OldProject.daniel.entity.post.tag.PostTag;
import eeit.OldProject.daniel.entity.post.tag.Tag;
import eeit.OldProject.daniel.service.PostTagService;

@RestController
@RequestMapping("/api/tags")
public class PostTagController {

    @Autowired
    private PostTagService service;

    @GetMapping
    public List<Tag> getAllTags() {
        return service.findAllTags();
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(service.createTag(tag));
    }

    @PostMapping("/assign-batch")
    public ResponseEntity<List<PostTag>> assignBatch(
            @RequestBody PostTagAssignBatchDto dto) {
        return ResponseEntity.ok(
            service.assignTags(dto.getPostId(), dto.getTagIds())
        );
    }

    @PostMapping("/assign")
    public ResponseEntity<PostTag> assign(
            @RequestParam Long postId,
            @RequestParam Long tagId) {
        return ResponseEntity.ok(
            service.assignTag(postId, tagId)
        );
    }

    @DeleteMapping("/assign/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.removeAssignment(id);
        return ResponseEntity.noContent().build();
    }
}