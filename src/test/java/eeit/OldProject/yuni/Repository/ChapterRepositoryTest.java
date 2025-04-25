package eeit.OldProject.yuni.Repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.yuni.Entity.Chapter;

@SpringBootTest
class ChapterRepositoryTest {
	
@Autowired
	private ChapterRepository chapterRepository;
	
	@Test
	void testFindByCourse_CourseIdOrderByPositionAsc() {
		List<Chapter> chapters =chapterRepository.findByCourse_CourseIdOrderByPositionAsc(3);
		
		for (Chapter chapter : chapters) {
            System.out.println("找到的章節："+chapter.getTitle()+"， 順序："+chapter.getPosition());
        }
	}

}
