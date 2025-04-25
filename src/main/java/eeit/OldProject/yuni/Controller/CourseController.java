package eeit.OldProject.yuni.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuni.Entity.Category;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/search")
    public List<Course> searchCoursesByKeyword(@RequestParam String keyword) {
        return courseService.searchCoursesByKeyword(keyword);
    }

    @GetMapping("/category/{category}")
    public List<Course> getCoursesByCategory(@PathVariable Category category) {
        return courseService.getCoursesByCategory(category);
    }

    @PostMapping("/admin")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/admin/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
    }
    
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCourseImage(@PathVariable Integer id) {
        Optional<Course> optional = courseService.getCourseById(id);
        if (optional.isPresent() && optional.get().getCoverImage() != null) {
            byte[] image = optional.get().getCoverImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}