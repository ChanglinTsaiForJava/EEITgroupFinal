package eeit.OldProject.daniel.repository.post;

import java.util.List;

import eeit.OldProject.daniel.dto.PostFilter;
import eeit.OldProject.daniel.entity.post.Post;

public interface PostInterface {

	List<Post> searchPosts(PostFilter filter);

}