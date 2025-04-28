package eeit.OldProject.allen.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eeit.OldProject.allen.Dto.NewsPublicSearchRequest;
import eeit.OldProject.allen.Dto.NewsSearchRequest;
import eeit.OldProject.allen.Entity.News;

public interface NewsService {
	// 基本的CRUD
	News getNewsById(Integer id);

	Page<News> getAllNews(Pageable pageable);

	News createNews(News news);

	News updateNews(Integer id, News updateNews);

	void deleteById(Integer id);

	// 搜尋功能
	Page<News> searchFlexiblePaged(NewsSearchRequest searchRequest, Pageable pageable);

	// 發布新聞功能(status = 0 >> status = 1)
	News publishNews(Integer id);

	// 下架新聞(status由 1 >> 0 )
	News unpublishNews(Integer id);

	// ViewConut +1
	News viewNewsById(Integer id);
	
	// 新增前台搜尋
	Page<News> searchPublicNewsPaged(NewsPublicSearchRequest searchRequest, Pageable pageable);

}
