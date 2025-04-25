package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.EmployeeRating;
import eeit.OldProject.steve.Service.EmployeeRatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee-rating")
public class EmployeeRatingController {

    @Autowired
    private EmployeeRatingService employeeRatingService;

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody EmployeeRating rating, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        employeeRatingService.addRating(rating, userId);
        return ResponseEntity.ok("照護員評價新增成功");
    }

    @GetMapping("/all")
    public List<EmployeeRating> getAllRatings() {
        return employeeRatingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingById(@PathVariable Long id) {
        Optional<EmployeeRating> rating = employeeRatingService.getRatingById(id);

        if (rating.isPresent()) {
            return ResponseEntity.ok(rating.get());
        } else {
            return ResponseEntity.status(404).body("找不到此評價");
        }
    }


    @GetMapping("/by-caregiver/{caregiverId}")
    public List<EmployeeRating> getRatingsByCaregiverId(@PathVariable Long caregiverId) {
        return employeeRatingService.getRatingsByCaregiverId(caregiverId);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRating(@PathVariable Long id, @RequestBody EmployeeRating updated, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<EmployeeRating> existingOpt = employeeRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        EmployeeRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限修改此評價");
        }

        employeeRatingService.updateRating(existing, updated);
        return ResponseEntity.ok("評價更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<EmployeeRating> existingOpt = employeeRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        EmployeeRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限刪除此評價");
        }

        employeeRatingService.deleteRating(existing);
        return ResponseEntity.ok("評價已刪除");
    }
}

