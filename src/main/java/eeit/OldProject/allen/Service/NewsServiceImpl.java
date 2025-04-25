package eeit.OldProject.allen.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService{
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Override
	public News getNewsById(Integer id) {
		Optional<News> news = newsRepository.findById(id);
		return news.orElse(null);
	}
	
	@Override
	public List<News> getAllNews(){
		return newsRepository.findAll();
	}
	
	@Override
	public News createNews(News news) {
		return newsRepository.save(news);
	}
	
	@Override
	public News updateNews(Integer id, News updateNews) {
		//查詢資料ById
		Optional<News> newsOptional  = newsRepository.findById(id);
		//Optional.map():如果有值才做處理(isPresnet判斷)
		if(newsOptional.isPresent()) {
			//從查詢結果中拿出那一筆存在的新聞
			News existing = newsOptional.get();
			
			//接下來我要對它進行欄位更新
			//updateNews:前端傳來的資料，代表「你希望更新成什麼內容」
			//getTitle():從前端資料中取出標題(String)，當作新標題
			//existing:原本資料庫查詢到的新聞物件
			//setTitle():把新標題存到原本的新聞物件上
			existing.setTitle(updateNews.getTitle());
			existing.setThumbnail(updateNews.getThumbnail());
	        existing.setCategory(updateNews.getCategory());
	        existing.setCreateBy(updateNews.getCreateBy());
	        existing.setCreateAt(updateNews.getCreateAt());
	        existing.setPublishAt(updateNews.getPublishAt());
	        existing.setModifyBy(updateNews.getModifyBy());
	        existing.setModifyAt(updateNews.getModifyAt());
	        existing.setStatus(updateNews.getStatus());
	        existing.setContent(updateNews.getContent());
	        existing.setViewCount(updateNews.getViewCount());
	        existing.setTags(updateNews.getTags());

	        //儲存並回傳更新後的資料
	        return newsRepository.save(existing);
		} else {
			throw new RuntimeException("找不到此新聞 id:" + id);
		}
			 
	}
	
	@Override
	public void deleteById(Integer id) {
		if(newsRepository.existsById(id)) {
			newsRepository.deleteById(id);
		} else {
			throw new RuntimeException("找不到此新聞 id:" + id);
		}
	}
		
	//彈性搜尋
	@Override
	public Page<News> searchFlexiblePaged(String keyword, Integer categoryId, Pageable pageable) {
	    String trimmed = (keyword != null) ? keyword.trim() : null;
	    return newsRepository.searchFlexiblePaged(trimmed, categoryId, pageable);
	}
	

}
