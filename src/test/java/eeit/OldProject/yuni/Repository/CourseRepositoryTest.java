package eeit.OldProject.yuni.Repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import eeit.OldProject.yuni.Entity.Category;
import eeit.OldProject.yuni.Entity.Course;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    //	  分類搜尋(enum)
    //  List<Course> findByCategory(Category category);
    @Test
    public void testFindByCategory() {
        List<Course> result = courseRepository.findByCategory(Category.nutrition);
        System.out.println("找到的課程：" + result);
        for (Course course : result) {
            System.out.println("課程標題：" + course.getTitle());
        }
        System.out.println("找到的課程數量：" + result.size());
    }
    

    
    //    模糊search
    //    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
    //    List<Course> searchByKeyword(@Param("keyword") String keyword);
    @Test
    public void testSearchByKeyword() {
        String keyword = "照護"; 
        List<Course> result = courseRepository.searchByKeyword(keyword);

        System.out.println("模糊搜尋關鍵字：" + keyword + "找到課程數量：" + result.size());

        for (Course course : result) {
            System.out.println("課程標題：" + course.getTitle());
            System.out.println("課程描述：" + course.getDescription());
        }
    }

}
