package eeit.OldProject.allen.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.allen.Entity.NewsCategory;
import eeit.OldProject.allen.Service.NewsCategoryService;

@RestController
@RequestMapping("/news/category")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsCategoryController {
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	//查詢某一筆分類
	@GetMapping("/{id}")
	public NewsCategory getNewsCategoryById(@PathVariable Integer id) {
	    return newsCategoryService.getNewsCategoryById(id);
	}
	
	//查詢所有分類
	@GetMapping
	public List<NewsCategory> getAllCategories() {
	    return newsCategoryService.getAllCategories();
	}
	
	//新增一筆分類
    @PostMapping
    public NewsCategory createCategory(@RequestBody NewsCategory newsCategory) {
        return newsCategoryService.createNewsCategory(newsCategory);
    }
	
    //修改一筆分類
    @PutMapping("/{id}")
    public NewsCategory updateCategory(@PathVariable Integer id, @RequestBody NewsCategory updatedCategory) {
        return newsCategoryService.updateNewsCategory(id, updatedCategory);
    }
    
    //安全刪除一筆分類
    @DeleteMapping("/{id}")
    public void safeDeleteCategory(@PathVariable Integer id) {
        newsCategoryService.safeDeleteNewsCategoryById(id);
    }
}
