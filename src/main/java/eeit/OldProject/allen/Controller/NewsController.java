package eeit.OldProject.allen.Controller;

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    // 取得所有新聞
    @GetMapping
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // 根據 ID 查詢單一新聞
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Integer id) {
        Optional<News> optional = newsRepository.findById(id);
        return optional.orElse(null);
    }

    // 新增一則新聞
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsRepository.save(news);
    }

    // 修改新聞（PUT）
    @PutMapping("/{id}")
    public News updateNews(@PathVariable Integer id, @RequestBody News news) {
        news.setNewsId(id); // 確保 ID 設定正確
        return newsRepository.save(news);
    }

    // 刪除新聞
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Integer id) {
        newsRepository.deleteById(id);
    }
}