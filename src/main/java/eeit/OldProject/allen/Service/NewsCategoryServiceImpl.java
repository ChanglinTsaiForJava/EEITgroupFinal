package eeit.OldProject.allen.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.allen.Entity.NewsCategory;
import eeit.OldProject.allen.Repository.NewsCategoryRepository;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService{
	
	@Autowired
	private NewsCategoryRepository newsCategoryRepository;
	
	@Override
	public NewsCategory getNewsCategoryById(Integer id) {
	    return newsCategoryRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("找不到此分類 id: " + id));
	}
	
	@Override
	public List<NewsCategory> getAllCategories(){
		return newsCategoryRepository.findAll();
	}
	
	@Override
	public NewsCategory createNewsCategory(NewsCategory newsCategory) {
		return newsCategoryRepository.save(newsCategory);
	}
	
	@Override
	public NewsCategory updateNewsCategory(Integer id, NewsCategory updatedCategory) {
	    Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(id);

	    if (newsCategoryOptional.isPresent()) {
	        NewsCategory existing = newsCategoryOptional.get();

	        // ✅ 更新欄位
	        existing.setCategoryName(updatedCategory.getCategoryName());

	        // ✅ 儲存並回傳
	        return newsCategoryRepository.save(existing);
	    } else {
	        throw new RuntimeException("找不到此分類 id: " + id);
	    }
	}

	@Override
	public void deleteNewsCategoryById(Integer id) {
	    if (newsCategoryRepository.existsById(id)) {
	        newsCategoryRepository.deleteById(id);
	    } else {
	        throw new RuntimeException("找不到此分類 id: " + id);
	    }
	}
}
