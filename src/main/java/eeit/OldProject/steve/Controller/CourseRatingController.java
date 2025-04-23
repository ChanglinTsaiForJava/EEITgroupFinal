package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.CourseRating;
import eeit.OldProject.steve.Service.CourseRatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course-rating")
public class CourseRatingController {

    @Autowired
    private CourseRatingService courseRatingService;

    // ✅ 新增評價
    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody CourseRating rating, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        courseRatingService.addRating(rating, userId);
        return ResponseEntity.ok("課程評價新增成功");
    }

    // ✅ 查詢全部評價
    @GetMapping("/all")
    public List<CourseRating> getAllRatings() {
        return courseRatingService.getAllRatings();
    }

    // ✅ 查詢單一評價
    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingById(@PathVariable Long id) {
        Optional<CourseRating> rating = courseRatingService.getRatingById(id);

        if (rating.isPresent()) {
            return ResponseEntity.ok(rating.get());
        } else {
            return ResponseEntity.status(404).body("找不到此評價");
        }
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<?> getRatingsByCourse(@PathVariable Long courseId) {
        List<CourseRating> ratings = courseRatingService.getRatingsByCourseId(courseId);
        if (ratings.isEmpty()) {
            return ResponseEntity.status(404).body("此課程尚無評價");
        }
        return ResponseEntity.ok(ratings);
    }

    // ✅ 編輯評價
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRating(@PathVariable Long id,
                                        @RequestBody CourseRating updatedRating,
                                        HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<CourseRating> existingOpt = courseRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        CourseRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限修改此評價");
        }

        courseRatingService.updateRating(existing, updatedRating);
        return ResponseEntity.ok("評價更新成功");
    }

    // ✅ 刪除評價
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<CourseRating> existingOpt = courseRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        CourseRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限刪除此評價");
        }

        courseRatingService.deleteRating(existing);
        return ResponseEntity.ok("評價已刪除");
    }
}
