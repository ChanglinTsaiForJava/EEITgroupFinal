package eeit.OldProject.allen.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Service.NewsService;

@RestController
@RequestMapping("/news/public")
public class NewsPublicController {

    @Autowired
    private NewsService newsService;

    // 前台查全部已發布新聞(支援排序)
    @GetMapping
    public Page<News> getPublishedNews(
        @PageableDefault(sort = "publishAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
        Pageable pageable) {
        return newsService.searchFlexiblePaged(null, null, 1, null, null, pageable);
    }

    // 前台查單筆已發布新聞（避免查到草稿 ， 含viewcount +1 ）
    @GetMapping("/{id}")
    public News getPublishedNewsById(@PathVariable Integer id) {
        News news = newsService.getNewsById(id);
        if (news.getStatus() != 1) {
            throw new RuntimeException("此新聞尚未發布");
        }
        return newsService.viewNewsById(id); // ✅ 自動 +1 viewCount
    }

    /**
     * 🔎 前台彈性搜尋（keyword + categoryId，僅查 status = 1）
     * 支援分頁 + 排序
     */
    @GetMapping("/search")
    public Page<News> searchPublishedNews(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
        @PageableDefault(sort = "publishAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
        Pageable pageable
    ) {
        return newsService.searchFlexiblePaged(keyword, categoryId, 1, dateFrom, dateTo, pageable);
    }
      

}