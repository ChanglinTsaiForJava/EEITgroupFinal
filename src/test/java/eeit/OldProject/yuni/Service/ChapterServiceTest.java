package eeit.OldProject.yuni.Service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Entity.ContentType;
import eeit.OldProject.yuni.Repository.ChapterRepository;
@SpringBootTest
class ChapterServiceTest {
	
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private ChapterRepository chapterRepository;
	
	

//    public List<Chapter> getAllChapters() {
//        return chapterRepository.findAll();
//    }
//	@Test
	void testGetAllChapters() {
		List<Chapter> chapters = chapterService.getAllChapters();
		for (Chapter chapter: chapters) {
			System.out.println(chapter);
		}
		System.out.println("取得章節數量：" + chapters.size());
		System.out.println("============================================");
	}

//	  public Optional<Chapter> getChapterById(Integer id) {
//    	return chapterRepository.findById(id);
//	  }
//	@Test
	void testGetChapterById() {
		Optional<Chapter> chapter = chapterService.getChapterById(25);
		if(chapter.isEmpty()) {
			System.out.println("找不到此章節");
		}else {
		System.out.println("編號25的章節是："+chapter.get().getTitle());
		}
		System.out.println("============================================");
	}

	
//    public Chapter createChapter(Chapter chapter) {
//        return chapterRepository.save(chapter);
//    }
//	@Test
	void testCreateChapter() {
		Chapter chapter = new Chapter();
		chapter.setTitle("ABC Testing");
		chapter.setContentType(ContentType.article);
		chapter.setPosition(10);
		
		Chapter savedchapter = chapterService.createChapter(chapter);
		System.out.println("建立章節成功，ID：" + savedchapter.getChapterId());
		System.out.println("============================================");
	}

//    public Chapter updateChapter(Integer id, Chapter chapter) {
//        chapter.setChapterId(id);
//        return chapterRepository.save(chapter);
//    }
//	@Test
	void testUpdateChapter() {

		// 原始章節（確認它存在資料庫中）
		Optional<Chapter> original = chapterRepository.findById(100);
		if (original.isEmpty()) {
			System.out.println("資料庫找不到章節 ID 100");
			return;
		}
		
		// 新的更新資料
		Chapter updatedChapter = new Chapter();
		updatedChapter.setTitle("更新後的章節標題");
		updatedChapter.setContentType(ContentType.article);
		updatedChapter.setPosition(99);
		updatedChapter.setCourse(original.get().getCourse()); // 保留原本課程（必要）

		// 呼叫更新方法
//		Chapter result = chapterService.updateChapter(38, updatedChapter);
		chapterService.updateChapter(100, updatedChapter);

//		System.out.println("更新章節成功");
//		System.out.println("章節 ID：" + result.getChapterId());
//		System.out.println("新標題：" + result.getTitle());
//		System.out.println("新位置：" + result.getPosition());
//		System.out.println("內容型態：" + result.getContentType());
//		System.out.println("============================================");
	}

//    public void deleteChapter(Integer id) {
//        chapterRepository.deleteById(id);
//    }
//	@Test
	void testDeleteChapter() {
		
//		chapterService.deleteChapter(42);
//		Optional<Chapter>chapter=chapterService.getChapterById(42);
//		if(chapter.isEmpty()) {
//			System.out.println("章節42刪除成功");
//		}else{
//			System.out.println("章節42刪除失敗");
//		}
//		// 檢查章節42是否原本存在
//		boolean existed1 = chapterService.getChapterById(42).isPresent();
//		chapterService.deleteChapter(42);
//		if (!chapterService.getChapterById(42).isPresent()) {
//			System.out.println("章節 42" + (existed1 ? " 刪除成功" : " 原本就不存在"));
//		} else {
//			System.out.println("章節 42" + " 刪除失敗");
//		}
		
		chapterService.deleteChapter(42);
	}
	
//    public List<Chapter> getChaptersByCourseId(Integer courseId) {
//        return chapterRepository.findByCourse_CourseIdOrderByPositionAsc(courseId);
//    }
//	@Test
	void testGetChaptersByCourseId() {
List<Chapter> chapters = chapterService.getChaptersByCourseId(12);
for(Chapter chapter : chapters) {
	System.out.println("章節名稱："+ chapter.getTitle());
}	
	}

}
