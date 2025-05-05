package eeit.OldProject.allen.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eeit.OldProject.allen.Dto.NewsPublicSearchRequest;
import eeit.OldProject.allen.Dto.NewsSearchRequest;
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
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此新聞 ID: " + id));
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
		}
		
		news.setCreateAt(LocalDateTime.now()); // 預設時間為當下
		news.setCreateBy("admin"); //預設為admin
		
		return newsRepository.save(news);
	}

	@Override
	public News updateNews(Integer id, News updateNews) {
	    Optional<News> newsOptional = newsRepository.findById(id);

	    if (newsOptional.isEmpty()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此新聞 ID: " + id);
	    }

	    News existing = newsOptional.get();

	    // ✅ 更新一般欄位
	    existing.setTitle(updateNews.getTitle());
	    existing.setThumbnail(updateNews.getThumbnail());
	    existing.setCategory(updateNews.getCategory());
	    existing.setModifyBy(updateNews.getModifyBy());
	    existing.setModifyAt(LocalDateTime.now()); // 系統更新時間
	    existing.setContent(updateNews.getContent());
	    existing.setTags(updateNews.getTags());

	    // ✅ 只在前端有傳值時才更新 publishAt，避免覆蓋成 null
	    if (updateNews.getPublishAt() != null) {
	        existing.setPublishAt(updateNews.getPublishAt());
	    }

	    // ✅ 狀態處理：若前端沒傳 status 就預設為草稿
	    if (updateNews.getStatus() == null) {
	        existing.setStatus((byte) 0);
	    } else {
	        existing.setStatus(updateNews.getStatus());
	    }

	    return newsRepository.save(existing);
	}
	
	// 刪除一筆
	@Override
	public void deleteById(Integer id) {
		if (newsRepository.existsById(id)) {
			newsRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此新聞 ID: " + id);
		}
	}

	// --------------------------------------------

	// 後台搜尋+排序功能
	@Override
	public Page<News> searchFlexiblePaged(NewsSearchRequest searchRequest, Pageable pageable) {
		String keyword = searchRequest.getKeyword();
	    Integer categoryId = searchRequest.getCategoryId();
	    Integer status = searchRequest.getStatus();
	    LocalDateTime dateFrom = searchRequest.getDateFrom();
	    LocalDateTime dateTo = searchRequest.getDateTo();
	    // ➡️ 如果status是-1，就轉成null（代表不篩選）
	    if (status != null && status == -1) {
	        status = null;
	    }
		return newsRepository.searchFlexiblePagedWithDateRange(keyword, categoryId, status, dateFrom, dateTo, pageable);
	}

	//前台搜尋
	@Override
	public Page<News> searchPublicNewsPaged(NewsPublicSearchRequest searchRequest, Pageable pageable) {
	    String keyword = searchRequest.getKeyword();
	    Integer categoryId = searchRequest.getCategoryId();
	    LocalDateTime dateFrom = searchRequest.getDateFrom();
	    LocalDateTime dateTo = searchRequest.getDateTo();

	    Integer status = 1; // ➡️ 固定只查已發布

	    return newsRepository.searchFlexiblePagedWithDateRange(
	        keyword, categoryId, status, dateFrom, dateTo, pageable
	    );
	}
	
	
	
	
	// 發布新聞
	@Override
	public News publishNews(Integer id) {
		News news = getNewsById(id); // ✅ 統一錯誤處理與查詢

		//若是已發布狀態就不用修改
		if (news.getStatus() == 1)
			return news;
		
		news.setStatus((byte) 1);

		 // ✅ 僅在第一次發布時設定 publishAt
	    if (news.getPublishAt() == null) {
	        news.setPublishAt(LocalDateTime.now());
	    }
	    
	    // ✅ 每次發布都更新 modifyAt
	    news.setModifyAt(LocalDateTime.now());
	    
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
