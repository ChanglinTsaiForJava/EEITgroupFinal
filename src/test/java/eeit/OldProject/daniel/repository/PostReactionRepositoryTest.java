package eeit.OldProject.daniel.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.entity.PostReaction;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;

@SpringBootTest
class PostReactionRepositoryTest {
	
    @Autowired
    private PostReactionRepository repo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

//    @Test
    void saveAndFindByPostAndUser() {
        Post post = postRepo.save(Post.builder().title("T").content("C").build());
        
        User user = new User();
        user.setUserName("U");
        userRepo.save(user);

        PostReaction r = PostReaction.builder()
            .post(post)
            .user(user)
            .postReaction((byte)1)
            .createdAt(LocalDateTime.now())
            .build();
        repo.save(r);

        Optional<PostReaction> found = repo.findByPostPostIdAndUserUserId(post.getPostId(), user.getUserId());
        System.out.println("found.get()="+found.get());
    }

    @Test
    void countByPostAndType() {
        Post post = postRepo.save(Post.builder().title("T2").content("C2").build());
        
        User user1 = new User();
        user1.setUserName("U");
        userRepo.save(user1);

        User user2 = new User();
        user2.setUserName("U");
        userRepo.save(user2);
        
        repo.save(PostReaction.builder().post(post).user(user1).postReaction((byte)1).createdAt(LocalDateTime.now()).build());
        repo.save(PostReaction.builder().post(post).user(user2).postReaction((byte)1).createdAt(LocalDateTime.now()).build());

        long count = repo.countByPostPostIdAndPostReaction(post.getPostId(), (byte)1);
        System.out.println("count="+count);
    }

}
