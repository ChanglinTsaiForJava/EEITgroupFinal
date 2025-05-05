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

    // 狀態篩選（單純查 status = 1 的新聞）
    Page<News> findByStatus(Integer status, Pageable pageable);

    // 🔍 搜尋 + 篩選 + 日期區間（支援分頁）— 已加入 countQuery
    @Query(
        value = """
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
        """,
        countQuery = """
            SELECT COUNT(n) FROM News n
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
        """
    )
    Page<News> searchFlexiblePagedWithDateRange(
        @Param("keyword") String keyword,
        @Param("categoryId") Integer categoryId,
        @Param("status") Integer status,
        @Param("dateFrom") LocalDateTime dateFrom,
        @Param("dateTo") LocalDateTime dateTo,
        Pageable pageable
    );

    // 查詢某分類底下有幾筆新聞（用於防止刪除分類）
    long countByCategoryCategoryId(Integer categoryId);
}