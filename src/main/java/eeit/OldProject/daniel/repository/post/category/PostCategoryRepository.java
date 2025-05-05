package eeit.OldProject.daniel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.post.PostCategory;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {}
