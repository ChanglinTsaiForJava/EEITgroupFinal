package eeit.OldProject.daniel.repository.post.tag;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.daniel.entity.post.tag.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findAllByTagNameIn(Collection<String> names);
}
