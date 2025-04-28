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
    public ResponseEntity<Reply> create(@RequestBody Reply reply) {
    	Reply create = replyService.create(reply);
    	if (create!=null) {
			URI uri = URI.create("/api/replies" + create.getReplyId());
			return ResponseEntity.created(uri).body(create);
    	}
        return ResponseEntity.noContent().build();
    }
    
	@PutMapping("/{id}")
	public ResponseEntity<Reply> modify(@PathVariable Long id, @RequestBody Reply reply) {
		if (id != null) {
			reply.setReplyId(id);
			Reply modify = replyService.modify(reply);
			if (modify!=null) {
				return ResponseEntity.ok(modify);
			}
		}
		return ResponseEntity.notFound().build();
	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        replyService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
