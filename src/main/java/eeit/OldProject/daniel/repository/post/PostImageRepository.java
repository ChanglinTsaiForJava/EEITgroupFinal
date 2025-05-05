package eeit.OldProject.daniel.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.post.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
	List<PostImage> findByPostPostId(Long postId);
}