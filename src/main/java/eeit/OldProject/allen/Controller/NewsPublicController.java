package eeit.OldProject.allen.Controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import eeit.OldProject.allen.Dto.NewsPublicSearchRequest;
import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Service.NewsService;

@RestController
@RequestMapping("/news/public")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsPublicController {

    @Autowired
    private NewsService newsService;

    // 前台查全部已發布新聞(支援排序)
    @PostMapping
    public Page<News> getPublishedNews(
        @RequestBody(required = false) NewsPublicSearchRequest searchRequest,
        @PageableDefault(sort = "publishAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
        Pageable pageable) {

        // ➡️ 如果前端沒有傳任何搜尋條件，自己建一個空的Request物件
        if (searchRequest == null) {
            searchRequest = new NewsPublicSearchRequest();
        }

        return newsService.searchPublicNewsPaged(searchRequest, pageable);
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
    @PostMapping("/search")
    public Page<News> searchPublishedNews(
        @RequestBody NewsPublicSearchRequest searchRequest,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false, defaultValue = "publishAt") String sortBy, // ✅ 新增排序欄位
        @RequestParam(required = false, defaultValue = "desc") String sortDirection // ✅ 排序方向
    ) {
    	 // 排序字段白名單，防止 SQL Injection
        List<String> allowedSortFields = Arrays.asList("publishAt", "viewCount", "modifyAt");
        // 如果 sortBy 不在白名單內，默認為 publishAt
        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "publishAt";
        }
        // 設置排序方向，預設為 DESC
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        // 動態排序
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        return newsService.searchPublicNewsPaged(searchRequest, pageable);
    }
      

}