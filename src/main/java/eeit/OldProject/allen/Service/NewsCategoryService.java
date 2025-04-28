package eeit.OldProject.allen.Service;

import java.util.List;

import eeit.OldProject.allen.Entity.NewsCategory;

public interface NewsCategoryService {
	//基本的CRUD
	NewsCategory getNewsCategoryById(Integer id);
	List<NewsCategory> getAllCategories();
	NewsCategory createNewsCategory(NewsCategory newsCategory);
	NewsCategory updateNewsCategory(Integer id, NewsCategory updatedCategory);
	//void deleteNewsCategoryById(Integer id);
	
	//安全刪除
	void safeDeleteNewsCategoryById(Integer id);

}
