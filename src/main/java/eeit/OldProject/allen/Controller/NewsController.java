package eeit.OldProject.allen.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	//查詢某一筆新聞
	@GetMapping("/{id}")
	public News getNewsById(@PathVariable Integer id) {
		return newsService.getNewsById(id);
	}
	
	//查詢所有新聞
	@GetMapping
	public List<News> getAllNews() {
		return newsService.getAllNews();
	}
	
	//新增一筆新聞
	@PostMapping
	public News createNews(@RequestBody News news) {
		return newsService.createNews(news);
	}
	
	//更新一筆新聞
	@PutMapping("/{id}")
	public News updateNews(@PathVariable Integer id, @RequestBody News updateNews) {
		return newsService.updateNews(id, updateNews);
	}
	
	//刪除一筆新聞
	@DeleteMapping("/{id}")
	public void deleteNews(@PathVariable Integer id) {
		newsService.deleteById(id);
	}
	
	//彈性搜尋:keyword 和 categoryId 都是可選參數
	@GetMapping("/search")
	public Page<News> searchFlexiblePaged(
	        @RequestParam(required = false) String keyword,
	        @RequestParam(required = false) Integer categoryId,
	        @PageableDefault(page = 0, size = 5, sort = "publishAt", direction = Sort.Direction.DESC)
	        Pageable pageable) {
	    
	    return newsService.searchFlexiblePaged(keyword, categoryId, pageable);
	}
}
