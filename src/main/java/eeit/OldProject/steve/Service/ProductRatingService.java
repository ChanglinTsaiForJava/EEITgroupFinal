package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.ProductRating;

import java.util.List;
import java.util.Optional;

public interface ProductRatingService {
    ProductRating addRating(ProductRating rating, Long userId);
    List<ProductRating> getAllRatings();
    Optional<ProductRating> getRatingById(Long id);
    ProductRating updateRating(ProductRating existing, ProductRating updated);
    void deleteRating(ProductRating rating);
    List<ProductRating> getRatingsByProductId(Long productId);
}