package eeit.OldProject.yuni.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import eeit.OldProject.yuni.Entity.Chapter;
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

//    @GetMapping
//    public List<Course> getAllCourses() {
//        return courseService.getAllCourses();
//    }

    @GetMapping
    public ResponseEntity<?> getAllCourse(){
        List<Course> courses = courseService.getAllCourses();
        if (!courses.isEmpty()) {
            return ResponseEntity.ok(courses);
        } else {
            return ResponseEntity.status(404).body("目前無任何課程");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(404).body("查無編號為"+id+"之課程");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCoursesByKeyword(@RequestParam(required = false) String keyword) {
        List<Course> courses;
        if (keyword == null || keyword.trim().isEmpty()) {
            courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        }
        courses = courseService.searchCoursesByKeyword(keyword);
        if (courses.isEmpty()) {
            return ResponseEntity.status(404).body("找不到符合 '" + keyword + "' 的課程");
        }
        return ResponseEntity.ok(courses);
    }

//@GetMapping("/category/{category}")
//public ResponseEntity<?> getCoursesByCategory(@PathVariable Category category) {
//    List<Course> courses = courseService.getCoursesByCategory(category);
//    if (courses.isEmpty()) {
//        return ResponseEntity.status(404).body("No courses found for "+ category);
//    } else {
//        return ResponseEntity.ok(courses);
//    }
//}

//    @GetMapping("/category/{category}")
//    public ResponseEntity<?> getCoursesByCategory(@PathVariable String category) {
//        if (!Category.exists(category)) {
//            return ResponseEntity.status(400).body("查無此類別");
//        }
//        Category categoryEnum = Category.valueOf(category.toLowerCase());
//        List<Course> courses = courseService.getCoursesByCategory(categoryEnum);
//        if (courses.isEmpty()) {
//            return ResponseEntity.status(404).body("查無此類別（" + category+")之課程");
//        } else {
//            return ResponseEntity.ok(courses);
//        }
//
//    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getCoursesByCategory(@PathVariable String category) {
        if (!Category.exists(category)) {
            return ResponseEntity.status(400).body("查無此類別");
        }

        Category categoryEnum = Category.getByNameIgnoreCase(category);
        List<Course> courses = courseService.getCoursesByCategory(categoryEnum);

        if (courses.isEmpty()) {
            return ResponseEntity.status(404).body("查無此類別（" + category + "）之課程");
        } else {
            return ResponseEntity.ok(courses);
        }
    }



    @PostMapping("/admin")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        if(course==null || course.getTitle()==null || course.getTitle().isEmpty()){
        return ResponseEntity.status(400).body("課程標題不可為空白");
        }
        Course created = courseService.createCourse(course);
        return ResponseEntity.ok(created);
    }

//    @PutMapping("/admin/{id}")
////    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
////        return courseService.updateCourse(id, course);
////    }
        @PutMapping("/admin/{id}")
        public ResponseEntity<?> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        if (course ==null){return ResponseEntity.badRequest().build();}
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isEmpty()){
            return ResponseEntity.status(404).body("課程 " + id + " 原本就不存在");
        }
        Course update = courseOptional.get();
        if(course.getTitle()!=null && !course.getTitle().isEmpty()){
            update.setTitle(course.getTitle());
        }
        if(course.getDescription()!=null && !course.getDescription().isEmpty()){
            update.setDescription(course.getDescription());
        }
        if(course.getDuration()!=null && !course.getDuration().isEmpty()){
            update.setDuration(course.getDuration());
        }
        if(course.getIsProgressLimited()!=null){
            update.setIsProgressLimited(course.getIsProgressLimited());
        }
        if(course.getCategory()!=null){
            update.setCategory(course.getCategory());
        }
        if(course.getPrice()!=null){
        update.setPrice(course.getPrice());
        }
        Course saved = courseService.updateCourse(id, update);
        return ResponseEntity.ok(saved);    }


//    @DeleteMapping("/admin/{id}")
//    public void deleteCourse(@PathVariable Integer id) {
//        courseService.deleteCourse(id);
//    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return ResponseEntity.ok("課程 " + id + " 刪除成功");
        } else {
            return ResponseEntity.status(404).body("課程 " + id + " 原本就不存在");
        }
    }


//    @GetMapping("/{id}/image")
//    public ResponseEntity<byte[]> getCourseImage(@PathVariable Integer id) {
//        Optional<Course> optional = courseService.getCourseById(id);
//        if (optional.isPresent() && optional.get().getCoverImage() != null) {
//            byte[] image = optional.get().getCoverImage();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_PNG);
//            return new ResponseEntity<>(image, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//        @GetMapping("/{id}/image")
//    public ResponseEntity<?> getCourseImage(@PathVariable Integer id) throws IOException {
//        Optional<Course> optional = courseService.getCourseById(id);
//        if (optional.isPresent() && optional.get().getCoverImage() != null) {
//            byte[] image = optional.get().getCoverImage();
//            if (image == null) {image=loadDefaultImage();}
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_PNG);
//            return new ResponseEntity<>(image, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.status(404).body("查無課程封面");
//        }
//    }
//
//    private byte[] loadDefaultImage() throws IOException {
//            InputStream is = getClass().getResourceAsStream("/static/no-image.png");
//            if (is != null) {
//                return is.readAllBytes();
//            }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCourseImage(@PathVariable Integer id) throws IOException {
        Optional<Course> optional = courseService.getCourseById(id);
        byte[] imageBytes;

        if (optional.isPresent() && optional.get().getCoverImage() != null) {
            imageBytes = optional.get().getCoverImage();
        } else {
            imageBytes = loadDefaultImage();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    private byte[] loadDefaultImage() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/static/yuni/noimageavailable300.png")) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IOException("找不到預設圖片！");
            }
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCourseCount() {
        long count = courseService.getCourseCount();
        return ResponseEntity.ok(count);
    }
}
