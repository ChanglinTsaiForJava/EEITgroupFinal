package eeit.OldProject.allen.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import eeit.OldProject.allen.Entity.NewsCategory;
import eeit.OldProject.allen.Repository.NewsCategoryRepository;
import eeit.OldProject.allen.Repository.NewsRepository;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService{
	
	@Autowired
	private NewsCategoryRepository newsCategoryRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Override
	public NewsCategory getNewsCategoryById(Integer id) {
	    return newsCategoryRepository.findById(id)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此分類 id: " + id));
	}
	
	@Override
	public List<NewsCategory> getAllCategories(){
		return newsCategoryRepository.findAll();
	}
	
	@Override
	public NewsCategory createNewsCategory(NewsCategory newsCategory) {
		//如果分類名稱已存在，不能新增
		if(newsCategoryRepository.existsByCategoryName(newsCategory.getCategoryName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "分類名稱已存在，請換一個名稱");
		}
		return newsCategoryRepository.save(newsCategory);
	}
	
	@Override
	public NewsCategory updateNewsCategory(Integer id, NewsCategory updatedCategory) {
	    Optional<NewsCategory> newsCategoryOptional = newsCategoryRepository.findById(id);

	    if (newsCategoryOptional.isPresent()) {
	        NewsCategory existing = newsCategoryOptional.get();
	        
	        //名稱重複檢查（排除自己這筆）
	        if (newsCategoryRepository.existsByCategoryNameAndCategoryIdNot(updatedCategory.getCategoryName(), id)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "分類名稱已存在，請換一個名稱");
	        }
	        
	        // ✅ 更新欄位
	        existing.setCategoryName(updatedCategory.getCategoryName());

	        // ✅ 儲存並回傳
	        return newsCategoryRepository.save(existing);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此分類 ID: " + id);
	    }
	}

	//安全刪除:確保該分類底下沒有新聞才能刪除
	@Override
	public void safeDeleteNewsCategoryById(Integer id) {
		//檢查這個分類底下有沒有新聞
		long newsCount = newsRepository.countByCategoryCategoryId(id);
		if(newsCount > 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "刪除失敗:此分類尚有" + newsCount + "筆新聞，請先刪除或移動新聞");
		}
		
		//如果沒有新聞，即可安全刪除該分類
		//如果要刪除的category id不存在>>NOT FOUND
		if(!newsCategoryRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此分類 ID:" + id);
		}
		//安全刪除該NewsCategory
		newsCategoryRepository.deleteById(id);
	}
}
