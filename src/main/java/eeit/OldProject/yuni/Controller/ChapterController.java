package eeit.OldProject.yuni.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@GetMapping
	public List<Chapter> getAllChapters() {
		return chapterService.getAllChapters();
	}

	// @GetMapping("/{id}")
//    public ResponseEntity<Chapter> getChapterById(@PathVariable Integer id) {
//        return chapterService.getChapterById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
	@GetMapping("/{id}")
	public ResponseEntity<Chapter> getChapterById(@PathVariable Integer id) {
		Optional<Chapter> chapter = chapterService.getChapterById(id);
		if (chapter.isPresent()) {
			System.out.println("找到章節：" + chapter.get().getTitle());
			return ResponseEntity.ok(chapter.get());
		} else {
			System.out.println("找不到章節 ID: " + id);
			return ResponseEntity.notFound().build();
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

	@GetMapping("/course/{courseId}")
	public ResponseEntity<List<Chapter>> getChaptersByCourseId(@PathVariable Integer courseId) {
		List<Chapter> chapters = chapterService.getChaptersByCourseId(courseId);

		if (chapters.isEmpty()) {
			System.out.println("沒有此課程 ID: " + courseId + " 的章節");
			return ResponseEntity.notFound().build();
		} else {
			System.out.println("找到章節數量：" + chapters.size());
			return ResponseEntity.ok(chapters);
		}
	}

	//
	@PostMapping("/admin")
	public Chapter createChapter(@RequestBody Chapter chapter) {
		return chapterService.createChapter(chapter);
	}

//    @PutMapping("/admin/{id}")
//    public Chapter updateChapter(@PathVariable Integer id, @RequestBody Chapter chapter) {
//        if (chapter != null && chapter.getChapterId() != null) {
//            Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
//            if (chapterOptional != null && chapterOptional.isPresent()) {
//                Chapter update = chapterOptional.get();
//                if (chapter.getTitle() != null && !chapter.getTitle().isEmpty()) {
//                    update.setTitle(chapter.getTitle());
//                }
//                if (chapter.getPosition() != null && chapter.getPosition() != null) {
//                    update.setPosition(chapter.getPosition());
//                }
//                if (chapter.getContentType() != null && chapter.getContentType() != null) {
//                    update.setContentType(chapter.getContentType());
//                }
//                if (chapter.getContentUrl() != null && !chapter.getContentUrl().isEmpty()) {
//                    update.setContentUrl(chapter.getContentUrl());
//                }
//                if (chapter.getCourse() != null && chapter.getCourse() != null) {
//                    update.setCourse(chapter.getCourse());
//                }
//            }
//        }
//
//        return chapterService.updateChapter(id, update);
//    }

	@PutMapping("/admin/{id}")
	public ResponseEntity<Chapter> updateChapter(@PathVariable Integer id, @RequestBody Chapter chapter) {
	    if (chapter == null) {
	        return ResponseEntity.badRequest().build();
	    }

		Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
		if (chapterOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
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
			update.setCourse(chapter.getCourse());
		}


		Chapter saved = chapterService.updateChapter(id, update);
		return ResponseEntity.ok(saved);
	}

//
//    @DeleteMapping("/admin/{id}")
//    public void deleteChapter(@PathVariable Integer id) {
//        chapterService.deleteChapter(id);
//    }
//    
}

//// 刪除章節
//@DeleteMapping("/{id}")
//public ResponseEntity<String> deleteChapter(@PathVariable Integer id) {
//    boolean deleted = chapterService.deleteChapter(id);
//    if (deleted) {
//        return ResponseEntity.ok("章節 " + id + " 刪除成功");
//    } else {
//        return ResponseEntity.status(404).body("章節 " + id + " 原本就不存在");
//    }
//}
//
