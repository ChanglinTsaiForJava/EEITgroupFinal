package eeit.OldProject.yuni.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Repository.ChapterRepository;
import eeit.OldProject.yuni.Repository.CourseRepository;

@Service
public class ChapterService {

	@Autowired
	private ChapterRepository chapterRepository;
	@Autowired
	private CourseRepository courseRepository;

	public List<Chapter> getAllChapters() {
		return chapterRepository.findAll();
	}

	public Optional<Chapter> getChapterById(Integer id) {
		return chapterRepository.findById(id);
	}


	
	public Chapter createChapter(Chapter chapter) {
	    if (chapter.getCourse() != null && chapter.getCourse().getCourseId() != null) {
	        Optional<Course> courseOpt = courseRepository.findById(chapter.getCourse().getCourseId());
	        courseOpt.ifPresent(chapter::setCourse);
	    }

	    return chapterRepository.save(chapter);
	}
	

	public Chapter updateChapter(Integer id, Chapter chapter) {
		if (!chapterRepository.existsById(id)) {
			System.out.println("ID " + id + " 不存在，無法更新");
			return null;
		}

		chapter.setChapterId(id);
		Chapter updatedChapter = chapterRepository.save(chapter);

		System.out.println("更新章節成功");
		System.out.println("章節 ID：" + updatedChapter.getChapterId());
		System.out.println("新標題：" + updatedChapter.getTitle());
		System.out.println("新位置：" + updatedChapter.getPosition());
		System.out.println("內容型態：" + updatedChapter.getContentType());
		System.out.println("============================================");

		return updatedChapter;
	}

	public boolean deleteChapter(Integer id) {

		if (chapterRepository.existsById(id)) {
			chapterRepository.deleteById(id);
			System.out.println("章節 " + id + " 刪除成功");
			return true;

		} else {
			System.out.println("章節 " + id + " 原本就不存在");
			return false;
		}
	}

	public List<Chapter> getChaptersByCourseId(Integer courseId) {

		return chapterRepository.findByCourse_CourseIdOrderByPositionAsc(courseId);
	}

}
