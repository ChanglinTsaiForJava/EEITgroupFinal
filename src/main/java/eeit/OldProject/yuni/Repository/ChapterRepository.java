package eeit.OldProject.yuni.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
	
	List<Chapter> findByCourse_CourseIdOrderByPositionAsc(Integer courseId);
	
}
