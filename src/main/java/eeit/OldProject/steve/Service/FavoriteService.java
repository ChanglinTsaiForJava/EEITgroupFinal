package eeit.OldProject.steve.Service;


import eeit.OldProject.steve.Entity.Favorite_course;
import eeit.OldProject.steve.Entity.Favorite_employee;
import eeit.OldProject.steve.Entity.Favorite_product;
import eeit.OldProject.steve.Repository.Favorite_courseRepository;
import eeit.OldProject.steve.Repository.Favorite_empRepository;
import eeit.OldProject.steve.Repository.Favorite_productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private Favorite_courseRepository favoriteCourseRepository;

    @Autowired
    private Favorite_empRepository favoriteEmpRepository;

    @Autowired
    private Favorite_productRepository favoriteProductRepository;

    // 新增收藏課程
    public Favorite_course addFavoriteCourse(Long userId, Long courseId) {
        Favorite_course favorite = new Favorite_course();
        favorite.setUserId(userId);
        favorite.setCourseId(courseId);
        favorite.setArchivedDate(LocalDateTime.now());
        return favoriteCourseRepository.save(favorite);
    }

    // 新增收藏員工（照顧者）
    public Favorite_employee addFavoriteEmployee(Long userId, Long caregiverId) {
        Favorite_employee favorite = new Favorite_employee();
        favorite.setUserId(userId);
        favorite.setCaregiverId(caregiverId);
        favorite.setArchivedDate(LocalDateTime.now());
        return favoriteEmpRepository.save(favorite);
    }

    // 新增收藏產品
    public Favorite_product addFavoriteProduct(Long userId, Long productId) {
        Favorite_product favorite = new Favorite_product();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favorite.setArchivedDate(LocalDateTime.now());
        return favoriteProductRepository.save(favorite);
    }

    // 根據使用者獲取所有收藏課程
    public List<Favorite_course> getFavoriteCourses(Long userId) {
        return favoriteCourseRepository.findByUserId(userId);
    }

    // 根據使用者獲取所有收藏員工
    public List<Favorite_employee> getFavoriteEmployees(Long userId) {
        return favoriteEmpRepository.findByUserId(userId);
    }

    // 根據使用者獲取所有收藏產品
    public List<Favorite_product> getFavoriteProducts(Long userId) {
        return favoriteProductRepository.findByUserId(userId);
    }

    // 刪除收藏課程
    public void deleteFavoriteCourse(Long userId, Long courseId) {
        Favorite_course favorite = favoriteCourseRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new RuntimeException("收藏的課程未找到"));
        favoriteCourseRepository.delete(favorite);
    }

    // 刪除收藏員工（照顧者）
    public void deleteFavoriteEmployee(Long userId, Long caregiverId) {
        Favorite_employee favorite = favoriteEmpRepository.findByUserIdAndCaregiverId(userId, caregiverId)
                .orElseThrow(() -> new RuntimeException("收藏的員工未找到"));
        favoriteEmpRepository.delete(favorite);
    }

    // 刪除收藏產品
    public void deleteFavoriteProduct(Long userId, Long productId) {
        Favorite_product favorite = favoriteProductRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("收藏的產品未找到"));
        favoriteProductRepository.delete(favorite);
    }
}
