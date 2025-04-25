package eeit.OldProject.allen.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eeit.OldProject.allen.Entity.News;

public interface NewsService {
	//基本的CRUD
	News getNewsById(Integer id);
	List<News> getAllNews();
	News createNews(News news);
	News updateNews(Integer id, News updateNews);
	void deleteById(Integer id);
	
	//彈性搜尋
	Page<News> searchFlexiblePaged(String keyword, Integer categoryId, Pageable pageable);
}
