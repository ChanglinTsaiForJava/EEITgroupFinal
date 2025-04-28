package eeit.OldProject.yuni.Controller;

import java.util.List;
import java.util.Optional;

import eeit.OldProject.yuni.DTO.ChapterDto;
import eeit.OldProject.yuni.DTO.PrevNextChapterDto;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Service.ChapterService;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private CourseRepository courseRepository;

//	@GetMapping
//	public List<Chapter> getAllChapters() {
//		return chapterService.getAllChapters();
//	}

	@GetMapping
	public ResponseEntity<?> getAllChapters() {
		List<Chapter> chapters = chapterService.getAllChapters();
		if (!chapters.isEmpty()) {
			return ResponseEntity.ok(chapters);
		} else {
			return ResponseEntity.status(404).body("目前無任何章節");
		}
	}


	// @GetMapping("/{id}")
//    public ResponseEntity<Chapter> getChapterById(@PathVariable Integer id) {
//        return chapterService.getChapterById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

//	@GetMapping("/{id}")
//	public ResponseEntity<Chapter> getChapterById(@PathVariable Integer id) {
//		Optional<Chapter> chapter = chapterService.getChapterById(id);
//		if (chapter.isPresent()) {
//			System.out.println("找到章節：" + chapter.get().getTitle());
//			return ResponseEntity.ok(chapter.get());
//		} else {
//			System.out.println("找不到章節 ID: " + id);
//			return ResponseEntity.notFound().build();
//		}
//	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getChapterById(@PathVariable Integer id) {
		Optional<Chapter> chapter = chapterService.getChapterById(id);
		if (chapter.isPresent()) {
			return ResponseEntity.ok(chapter.get());
		} else {
			return ResponseEntity.status(404).body("找不到章節 ID:" + id);
		}
	}

//    @GetMapping("/{id}")
//    public Optional<Chapter> getChapterById(@PathVariable Integer id) {
//    	return chapterService.getChapterById(id);
//    }
//
//    @GetMapping("/course/{courseId}")
//    public List<Chapter> getChaptersByCourseId(@PathVariable Integer courseId) {
//        return chapterService.getChaptersByCourseId(courseId);
//    }

//	@GetMapping("/course/{courseId}")
//	public ResponseEntity<List<Chapter>> getChaptersByCourseId(@PathVariable Integer courseId) {
//		List<Chapter> chapters = chapterService.getChaptersByCourseId(courseId);
//
//		if (chapters.isEmpty()) {
//			System.out.println("沒有此課程 ID: " + courseId + " 的章節");
//			return ResponseEntity.notFound().build();
//		} else {
//			System.out.println("找到章節數量：" + chapters.size());
//			return ResponseEntity.ok(chapters);
//		}
//	}


	@GetMapping("/course/{courseId}")
	public ResponseEntity<?> getChaptersByCourseId(@PathVariable Integer courseId) {
		List<Chapter> chapters = chapterService.getChaptersByCourseId(courseId);
		if (chapters.isEmpty()) {
			return ResponseEntity.status(404).body("課程 ID " + courseId + " 下無章節資料");
		} else {
			return ResponseEntity.ok(chapters);
		}
	}

//	@PostMapping("/admin")
//	public Chapter createChapter(@RequestBody Chapter chapter) {
//		return chapterService.createChapter(chapter);
//	}

	@PostMapping("/admin")
	public ResponseEntity<?> createChapter(@RequestBody Chapter chapter) {
		if (chapter == null || chapter.getTitle() == null || chapter.getTitle().isEmpty()||chapter.getCourse()==null||chapter.getCourse().getCourseId()==null) {
			return ResponseEntity.badRequest().body("請提供有效的章節資料");
		}
		Chapter created = chapterService.createChapter(chapter);
		return ResponseEntity.ok(created);
	}


	@PutMapping("/admin/{id}")
	public ResponseEntity<?> updateChapter(@PathVariable Integer id, @RequestBody Chapter chapter) {
		if (chapter == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
		if (chapterOptional.isEmpty()) {
//			return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).body("章節 " + id + " 原本就不存在");
		}

		Chapter update = chapterOptional.get();

		if (chapter.getTitle() != null && !chapter.getTitle().isEmpty()) {
			update.setTitle(chapter.getTitle());
		}
		if (chapter.getPosition() != null) {
			update.setPosition(chapter.getPosition());
		}
		if (chapter.getContentType() != null) {
			update.setContentType(chapter.getContentType());
		}
		if (chapter.getContentUrl() != null && !chapter.getContentUrl().isEmpty()) {
			update.setContentUrl(chapter.getContentUrl());
		}
		if (chapter.getCourse() != null && chapter.getCourse().getCourseId() != null) {
			Optional<Course> courseOptional = courseRepository.findById(chapter.getCourse().getCourseId());
			if (courseOptional.isPresent()) {
				update.setCourse(courseOptional.get());
			} else {
				return ResponseEntity.badRequest().build();
			}
		}

		Chapter saved = chapterService.updateChapter(id, update);
		return ResponseEntity.ok(saved);
	}


	@DeleteMapping("/admin/{id}")
	public ResponseEntity<String> deleteChapter(@PathVariable Integer id) {
		boolean deleted = chapterService.deleteChapter(id);
		if (deleted) {
			return ResponseEntity.ok("章節 " + id + " 刪除成功");
		} else {
			return ResponseEntity.status(404).body("章節 " + id + " 原本就不存在");
		}

	}

	@GetMapping("/admin/{id}/prev-next")
	public ResponseEntity<?> getPrevAndNextChapter(@PathVariable Integer id) {
		Optional<Chapter> currentOpt = chapterService.getChapterById(id);
		if (currentOpt.isEmpty()) {
			return ResponseEntity.status(404).body("找不到章節 ID：" + id);
		}

		Chapter current = currentOpt.get();
		Integer courseId = current.getCourse().getCourseId();

		List<Chapter> allChapters = chapterService.getChaptersByCourseId(courseId);
		allChapters.sort((c1, c2) -> Integer.compare(
				c1.getPosition() == null ? Integer.MAX_VALUE : c1.getPosition(),
				c2.getPosition() == null ? Integer.MAX_VALUE : c2.getPosition()
		));

		Chapter prev = null;
		Chapter next = null;
		for (int i = 0; i < allChapters.size(); i++) {
			if (allChapters.get(i).getChapterId().equals(id)) {
				if (i > 0) prev = allChapters.get(i - 1);
				if (i < allChapters.size() - 1) next = allChapters.get(i + 1);
				break;
			}
		}
		PrevNextChapterDto response = new PrevNextChapterDto(
				prev != null ? new ChapterDto(prev) : null,
				next != null ? new ChapterDto(next) : null
		);
		return ResponseEntity.ok(response);
	}


}
