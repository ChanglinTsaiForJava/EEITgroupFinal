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

import eeit.OldProject.daniel.entity.Reply;
import eeit.OldProject.daniel.service.ReplyService;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {
	
	@Autowired
    private ReplyService replyService;

    @GetMapping
    public List<Reply> byComment(@RequestParam Long commentId) {
        return replyService.findByCommentId(commentId);
    }

    @PostMapping
    public ResponseEntity<Reply> create(@RequestBody Reply r) {
        return ResponseEntity.ok(replyService.save(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        replyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
