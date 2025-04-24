package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.Favorite_course;
import eeit.OldProject.steve.Entity.Favorite_employee;
import eeit.OldProject.steve.Entity.Favorite_product;
import eeit.OldProject.steve.Service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 新增收藏課程
    @PostMapping("/addCourse")
    public ResponseEntity<?> addFavoriteCourse(@RequestParam Long courseId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        Favorite_course favorite = favoriteService.addFavoriteCourse(userId, courseId);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    // 新增收藏員工（照顧者）
    @PostMapping("/addEmployee")
    public ResponseEntity<?> addFavoriteEmployee(@RequestParam Long caregiverId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        Favorite_employee favorite = favoriteService.addFavoriteEmployee(userId, caregiverId);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    // 新增收藏產品
    @PostMapping("/addProduct")
    public ResponseEntity<?> addFavoriteProduct(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        Favorite_product favorite = favoriteService.addFavoriteProduct(userId, productId);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    // 獲取所有收藏課程
    @GetMapping("/courses")
    public ResponseEntity<?> getFavoriteCourses(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        List<Favorite_course> favorites = favoriteService.getFavoriteCourses(userId);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    // 獲取所有收藏員工
    @GetMapping("/employees")
    public ResponseEntity<?> getFavoriteEmployees(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        List<Favorite_employee> favorites = favoriteService.getFavoriteEmployees(userId);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    // 獲取所有收藏產品
    @GetMapping("/products")
    public ResponseEntity<?> getFavoriteProducts(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        List<Favorite_product> favorites = favoriteService.getFavoriteProducts(userId);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    // 刪除收藏課程
    @DeleteMapping("/deleteCourse")
    public ResponseEntity<?> deleteFavoriteCourse(@RequestParam Long courseId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        favoriteService.deleteFavoriteCourse(userId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 刪除收藏員工（照顧者）
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteFavoriteEmployee(@RequestParam Long caregiverId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        favoriteService.deleteFavoriteEmployee(userId, caregiverId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 刪除收藏產品
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteFavoriteProduct(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");
        favoriteService.deleteFavoriteProduct(userId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}