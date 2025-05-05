package eeit.OldProject.daniel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.daniel.service.ReactionService;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {
	
  @Autowired private ReactionService svc;

  @PostMapping("/posts/{postId}")
  public ResponseEntity<Long> reactPost(
      @PathVariable Long postId,
      @RequestParam Long userId,
      @RequestParam Byte type
  ) {
    long count = svc.togglePostReaction(postId, userId, type);
    return ResponseEntity.ok(count);
  }

  @PostMapping("/comments/{commentId}")
  public ResponseEntity<Long> reactComment(
      @PathVariable Long commentId,
      @RequestParam Long userId,
      @RequestParam Byte type
  ) {
    long count = svc.toggleCommentReaction(commentId, userId, type);
    return ResponseEntity.ok(count);
  }

  @PostMapping("/replies/{replyId}")
  public ResponseEntity<Long> reactReply(
      @PathVariable Long replyId,
      @RequestParam Long userId,
      @RequestParam Byte type
  ) {
    long count = svc.toggleReplyReaction(replyId, userId, type);
    return ResponseEntity.ok(count);
  }
}
