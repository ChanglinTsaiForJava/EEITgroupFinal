package eeit.OldProject.allen.Controller;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import eeit.OldProject.allen.Dto.NewsSearchRequest;
import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Service.NewsService;

@RestController
@RequestMapping("/news/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsAdminController {

	@Autowired
	private NewsService newsService;

	// 查詢某一筆新聞
	@GetMapping("/{id}")
	public News getNewsById(@PathVariable Integer id) {
		return newsService.getNewsById(id);
	}

	// 查詢所有新聞(支援分頁 + 排序)
	@GetMapping
	public Page<News> getAllNews(
			@PageableDefault(sort = "publishAt", direction = Sort.Direction.DESC) Pageable pageable) {

		return newsService.getAllNews(pageable);
	}

	// 新增一筆新聞
	@PostMapping
	public News createNews(@RequestBody News news) {
		return newsService.createNews(news);
	}

	// 更新一筆新聞
	@PutMapping("/{id}")
	public News updateNews(@PathVariable Integer id, @RequestBody News updateNews) {
		return newsService.updateNews(id, updateNews);
	}

	// 刪除一筆新聞
	@DeleteMapping("/{id}")
	public void deleteNews(@PathVariable Integer id) {
		newsService.deleteById(id);
	}

	// 🔍 彈性搜尋（分類、關鍵字、狀態、時間 + 排序）
	@PostMapping("/search")
	public Page<News> searchFlexible(
			@RequestBody NewsSearchRequest searchRequest,
			Pageable pageable) {
		return newsService.searchFlexiblePaged(searchRequest, pageable);
	}

	// 發布新聞
	@PatchMapping("/{id}/publish")
	public News publishNews(@PathVariable Integer id) {
		return newsService.publishNews(id);
	}

	// 下架新聞
	@PatchMapping("/{id}/unpublish")
	public News unpublishNews(@PathVariable Integer id) {
		return newsService.unpublishNews(id);
	}

	//上傳圖片
	@PostMapping("/upload-thumbnail")
	public Map<String, String> uploadThumbnail(@RequestParam("file") MultipartFile file){
		try {
	        // 取得專案根目錄
	        String projectPath = System.getProperty("user.dir");
	        System.out.println("專案根目錄 = " + projectPath);
			
			// 檢查檔案格式
			String contentType = file.getContentType();
			if (contentType == null || 
			    !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
			    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "只允許上傳jpg或png圖片！");
			}
			//檢查檔案大小
			long maxSize = 5 * 1024 * 1024;
			if (file.getSize() > maxSize) {
			    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "檔案過大，請上傳小於5MB的圖片！");
			}
			
			 // 取得今天日期 ➔ 產生目錄路徑 例如：2025/04-28/
	        LocalDate today = LocalDate.now();
	        String datePath = today.getYear() + "/" + String.format("%02d", today.getMonthValue()) + "-" + String.format("%02d", today.getDayOfMonth()) + "/";
	        System.out.println("日期資料夾 = " + datePath);
			
			//確保目錄存在，完整的上傳目錄
			String uploadDir = projectPath  + "/uploads/news_thumbnails/" + datePath;
			File dir = new File(uploadDir);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			//生成唯一檔名
			String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			String filePath = uploadDir + filename;
			System.out.println("儲存路徑 filePath = " + filePath);
			
			//儲存檔案
			file.transferTo(new File(filePath));
			
			//回傳圖片URL(localhost)
			String imageUrl = "http://localhost:8082/uploads/news_thumbnails/" + datePath + filename;
			
			Map<String, String> result = new HashMap<>();
	        result.put("url", imageUrl);
	        return result;
			
		} catch (Exception e) {	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "圖片上傳失敗", e);
		}
	}
	
}
