package eeit.OldProject.allen.Repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eeit.OldProject.allen.Entity.News;


@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
	
	//狀態篩選
	Page<News> findByStatus(Integer status, Pageable pageable);
	
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
		    AND (:status IS NULL OR n.status = :status)
		    AND (:dateFrom IS NULL OR n.publishAt >= :dateFrom)
		    AND (:dateTo IS NULL OR n.publishAt <= :dateTo)
		""")
		Page<News> searchFlexiblePagedWithDateRange(
		    @Param("keyword") String keyword,
		    @Param("categoryId") Integer categoryId,
		    @Param("status") Integer status,
		    @Param("dateFrom") LocalDateTime dateFrom,
		    @Param("dateTo") LocalDateTime dateTo,
		    Pageable pageable
		);
	
	//某分類有幾筆新聞
	long countByCategoryCategoryId(Integer categoryId);
}