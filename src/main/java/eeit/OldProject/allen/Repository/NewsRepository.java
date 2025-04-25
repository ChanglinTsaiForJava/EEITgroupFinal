package eeit.OldProject.allen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eeit.OldProject.allen.Entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>{
		
	//彈性搜尋
	@Query("""
		    SELECT n FROM News n
		    WHERE 
		        (:categoryId IS NULL OR n.category.categoryId = :categoryId)
		    AND 
		        (
		            :keyword IS NULL 
		            OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
		            OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
		            OR LOWER(n.tags) LIKE LOWER(CONCAT('%', :keyword, '%'))
		        )
		""")
		Page<News> searchFlexiblePaged(
		    @Param("keyword") String keyword,
		    @Param("categoryId") Integer categoryId,
		    Pageable pageable
		);
	
}

