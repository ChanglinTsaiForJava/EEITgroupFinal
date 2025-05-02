package eeit.OldProject.daniel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUser_UserId(Long userId, Pageable pageable);
}
