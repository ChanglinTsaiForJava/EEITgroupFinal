package eeit.OldProject.daniel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
