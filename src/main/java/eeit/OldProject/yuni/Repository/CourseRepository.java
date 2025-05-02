package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.Category;
import eeit.OldProject.yuni.Entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // 模糊search
//    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
//    List<Course> searchByKeyword(@Param("keyword") String keyword);
    @Query("SELECT c FROM Course c WHERE c.title LIKE :keyword")
    List<Course> searchByKeyword(@Param("keyword") String keyword);

//    OR c.description LIKE :keyword



    // 分類搜尋(enum)
    List<Course> findByCategory(Category category);
}
