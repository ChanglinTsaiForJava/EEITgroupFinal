package eeit.OldProject.steve.Controller;
import eeit.OldProject.steve.Entity.EmployeeRating;
import eeit.OldProject.steve.Service.EmployeeRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-ratings")
public class EmployeeRatingController {

    @Autowired
    private EmployeeRatingService ratingService;

    @GetMapping
    public List<EmployeeRating> getAll() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRating> getById(@PathVariable Long id) {
        EmployeeRating rating = ratingService.getRatingById(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmployeeRating> create(@RequestBody EmployeeRating rating) {
        return ResponseEntity.ok(ratingService.createRating(rating));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRating> update(@PathVariable Long id, @RequestBody EmployeeRating rating) {
        EmployeeRating updated = ratingService.updateRating(id, rating);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}

