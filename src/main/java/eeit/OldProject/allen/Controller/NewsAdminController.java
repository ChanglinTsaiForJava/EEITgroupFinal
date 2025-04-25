package eeit.OldProject.allen.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Service.NewsService;

@RestController
@RequestMapping("/news/admin")
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
	@GetMapping("/search")
	public Page<News> searchFlexible(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false, defaultValue = "-1") Integer status,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
			Pageable pageable) {
		return newsService.searchFlexiblePaged(keyword, categoryId, status == -1 ? null : status, dateFrom, dateTo,
				pageable);
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

}
