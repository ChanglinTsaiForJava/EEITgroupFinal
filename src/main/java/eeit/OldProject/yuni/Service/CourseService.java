package eeit.OldProject.yuni.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuni.Entity.Category;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        List<Course> list = courseRepository.findAll();
        System.out.println("取出的課程資料：" + list);
        return list;
    }

    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

//    public List<Course> searchCoursesByKeyword(String keyword) {
//        return courseRepository.searchByKeyword(keyword);
//    }

    public List<Course> searchCoursesByKeyword(String keyword) {
        return courseRepository.searchByKeyword("%" + keyword + "%");
    }

    public List<Course> getCoursesByCategory(Category category) {
        return courseRepository.findByCategory(category);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course course) {
        course.setCourseId(id);
        return courseRepository.save(course);
    }

    public boolean deleteCourse(Integer id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;

        } else {
            return false;
        }
    }


    public Optional<Course> findById(Integer id) {
        return courseRepository.findById(id);
    }

    public long getCourseCount() {
        return courseRepository.count();
    }
}
