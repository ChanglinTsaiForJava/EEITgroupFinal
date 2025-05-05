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

import eeit.OldProject.daniel.dto.PostTopicAssignBatchDto;
import eeit.OldProject.daniel.entity.post.topic.PostTopic;
import eeit.OldProject.daniel.entity.post.topic.PostTopicClassifier;
import eeit.OldProject.daniel.service.PostTopicService;

@RestController
@RequestMapping("/api/topics")
public class PostTopicController {
    
    @Autowired
    private PostTopicService svc;

    @GetMapping
    public List<PostTopic> all() {
        return svc.findAllTopics();
    }

    @PostMapping
    public ResponseEntity<PostTopic> create(@RequestBody PostTopic t) {
        return ResponseEntity.ok(svc.createTopic(t));
    }

    @PostMapping("/assign-batch")
    public ResponseEntity<List<PostTopicClassifier>> batchAssign(
            @RequestBody PostTopicAssignBatchDto dto) {
        Long postId = dto.getPostId();
        List<Long> topicIds = dto.getTopicIds();
        return ResponseEntity.ok(svc.assignTopics(postId, topicIds));
    }
    
    @PostMapping("/assign")
    public ResponseEntity<PostTopicClassifier> assign(
            @RequestParam Long postId,
            @RequestParam Long topicId) {
        return ResponseEntity.ok(svc.assignTopic(postId, topicId));
    }

    @DeleteMapping("/assign/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        svc.removeAssignment(id);
        return ResponseEntity.noContent().build();
    }
}