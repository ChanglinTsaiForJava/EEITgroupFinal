package eeit.OldProject.allen.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsRepository newsRepository;

	// 查詢單筆
	@Override
	public News getNewsById(Integer id) {
	    return newsRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("找不到此新聞 ID: " + id));
	}

	// 查詢所有
	@Override
	public Page<News> getAllNews(Pageable pageable) {
		return newsRepository.findAll(pageable);
	}

	// 新增一筆
	@Override
	public News createNews(News news) {
		if (news.getStatus() == null) {
			news.setStatus((byte) 0); // 預設為草稿
			news.setCreateAt(LocalDateTime.now()); //預設時間為當下
		}
		return newsRepository.save(news);
	}

	// 修改一筆
	@Override
	public News updateNews(Integer id, News updateNews) {
		// 查詢資料ById
		Optional<News> newsOptional = newsRepository.findById(id);
		// Optional.map():如果有值才做處理(isPresnet()判斷)
		if (newsOptional.isPresent()) {
			// 從查詢結果中拿出那一筆存在的新聞
			News existing = newsOptional.get();

			// 接下來我要對它進行欄位更新
			// updateNews:前端傳來的資料，代表「你希望更新成什麼內容」
			// getTitle():從前端資料中取出標題(String)，當作新標題
			// existing:原本資料庫查詢到的新聞物件
			// setTitle():把新標題存到原本的新聞物件上
			existing.setTitle(updateNews.getTitle());
			existing.setThumbnail(updateNews.getThumbnail());
			existing.setCategory(updateNews.getCategory());
			existing.setPublishAt(updateNews.getPublishAt());
			existing.setModifyBy(updateNews.getModifyBy());
			existing.setModifyAt(LocalDateTime.now()); // 系統自動更新時間
			existing.setStatus(updateNews.getStatus());
			existing.setContent(updateNews.getContent());
			existing.setTags(updateNews.getTags());
			// 儲存並回傳更新後的資料
			return newsRepository.save(existing);
		} else {
			throw new RuntimeException("找不到此新聞 id:" + id);
		}
	}

	// 刪除一筆
	@Override
	public void deleteById(Integer id) {
		if (newsRepository.existsById(id)) {
			newsRepository.deleteById(id);
		} else {
			throw new RuntimeException("找不到此新聞 id:" + id);
		}
	}

	// --------------------------------------------

	//搜尋+排序功能
	@Override
	public Page<News> searchFlexiblePaged(String keyword, Integer categoryId, Integer status,
	                                      LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
	    return newsRepository.searchFlexiblePagedWithDateRange(
	        keyword, categoryId, status, dateFrom, dateTo, pageable);
	}
	
	// 發布新聞
	@Override
	public News publishNews(Integer id) {
		News news = getNewsById(id); // ✅ 統一錯誤處理與查詢
		
		if (news.getStatus() == 1) return news;
		news.setStatus((byte) 1);
		
		if (news.getPublishAt() == null) {
			news.setPublishAt(LocalDateTime.now());
		}
		return newsRepository.save(news);
	}

	// 下架新聞
	@Override
	public News unpublishNews(Integer id) {
		News news = getNewsById(id);
		news.setStatus((byte) 0);
		return newsRepository.save(news);
	}

	// ViewCount + 1
	@Override
	public News viewNewsById(Integer id) {
		News news = getNewsById(id);
		news.setViewCount(news.getViewCount() == null ? 1 : news.getViewCount() + 1);
		return newsRepository.save(news);
	}
	
	
}
