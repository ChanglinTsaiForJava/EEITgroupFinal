package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.ProductRating;
import eeit.OldProject.steve.Service.ProductRatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-rating")
public class ProductRatingController {

    @Autowired
    private ProductRatingService productRatingService;

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody ProductRating rating, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        productRatingService.addRating(rating, userId);
        return ResponseEntity.ok("產品評價新增成功");
    }

    @GetMapping("/all")
    public List<ProductRating> getAllRatings() {
        return productRatingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingById(@PathVariable Long id) {
        Optional<ProductRating> rating = productRatingService.getRatingById(id);

        if (rating.isPresent()) {
            return ResponseEntity.ok(rating.get());
        } else {
            return ResponseEntity.status(404).body("找不到此評價");
        }
    }

    @GetMapping("/by-product/{productId}")
    public List<ProductRating> getRatingsByProductId(@PathVariable Long productId) {
        return productRatingService.getRatingsByProductId(productId);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRating(@PathVariable Long id, @RequestBody ProductRating updated, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<ProductRating> existingOpt = productRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        ProductRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限修改此評價");
        }

        productRatingService.updateRating(existing, updated);
        return ResponseEntity.ok("評價更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        Optional<ProductRating> existingOpt = productRatingService.getRatingById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.status(404).body("找不到評價");

        ProductRating existing = existingOpt.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("無權限刪除此評價");
        }

        productRatingService.deleteRating(existing);
        return ResponseEntity.ok("評價已刪除");
    }
}

