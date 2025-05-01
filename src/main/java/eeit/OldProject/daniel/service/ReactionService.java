package eeit.OldProject.daniel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Comment;
import eeit.OldProject.daniel.entity.CommentReaction;
import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.entity.PostReaction;
import eeit.OldProject.daniel.entity.Reply;
import eeit.OldProject.daniel.entity.ReplyReaction;
import eeit.OldProject.daniel.repository.CommentReactionRepository;
import eeit.OldProject.daniel.repository.PostReactionRepository;
import eeit.OldProject.daniel.repository.ReplyReactionRepository;
import eeit.OldProject.steve.Entity.User;

@Service
@Transactional
public class ReactionService {
  @Autowired private PostReactionRepository   postReactRepo;
  @Autowired private CommentReactionRepository commentReactRepo;
  @Autowired private ReplyReactionRepository   replyReactRepo;

  /** 切換貼文按讚，回傳最新讚數 */
  public long togglePostReaction(Long postId, Long userId, Byte type) {
    postReactRepo.findByPostPostIdAndUserUserId(postId, userId)
      .ifPresentOrElse(
        postReactRepo::delete,
        () -> {
          PostReaction r = new PostReaction();
          r.setPostReaction(type);
          r.setUser(new User(){ { setUserId(userId); } });
          r.setPost(new Post(){ { setPostId(postId); } });
          postReactRepo.save(r);
        }
      );
    return postReactRepo.countByPostPostIdAndPostReaction(postId, type);
  }

  /** 評論按讚切換 */
  public long toggleCommentReaction(Long commentId, Long userId, Byte type) {
    commentReactRepo.findByCommentCommentIdAndUserUserId(commentId, userId)
      .ifPresentOrElse(
        commentReactRepo::delete,
        () -> {
          CommentReaction r = new CommentReaction();
          r.setCommentReaction(type);
          r.setUser(new User(){ { setUserId(userId); } });
          r.setComment(new Comment(){ { setCommentId(commentId); } });
          commentReactRepo.save(r);
        }
      );
    return commentReactRepo.countByCommentCommentIdAndCommentReaction(commentId, type);
  }

  /** 回覆按讚切換 */
  public long toggleReplyReaction(Long replyId, Long userId, Byte type) {
    replyReactRepo.findByReplyReplyIdAndUserUserId(replyId, userId)
      .ifPresentOrElse(
        replyReactRepo::delete,
        () -> {
          ReplyReaction r = new ReplyReaction();
          r.setReplyReaction(type);
          r.setUser(new User(){ { setUserId(userId); } });
          r.setReply(new Reply(){ { setReplyId(replyId); } });
          replyReactRepo.save(r);
        }
      );
    return replyReactRepo.countByReplyReplyIdAndReplyReaction(replyId, type);
  }
}