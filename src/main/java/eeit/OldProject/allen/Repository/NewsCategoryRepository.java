package eeit.OldProject.allen.Repository;

import eeit.OldProject.allen.Entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    
	//檢查分類名稱是否已存在
	boolean existsByCategoryName(String categoryName);
	
	//新名字不能跟其他分類的名字重複，但是自己原本的名字是可以接受的
	boolean existsByCategoryNameAndCategoryIdNot(String categoryName, Integer categoryId);
	
}
