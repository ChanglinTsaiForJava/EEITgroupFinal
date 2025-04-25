package eeit.OldProject.steve.Service;


import eeit.OldProject.steve.Entity.ProductRating;
import eeit.OldProject.steve.Repository.ProductRatingRepository;
import eeit.OldProject.steve.Service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public ProductRating addRating(ProductRating rating, Long userId) {
        rating.setUserId(userId);
        rating.setArchivedDate(LocalDateTime.now());
        return productRatingRepository.save(rating);
    }

    @Override
    public List<ProductRating> getAllRatings() {
        return productRatingRepository.findAll();
    }

    @Override
    public Optional<ProductRating> getRatingById(Long id) {
        return productRatingRepository.findById(id);
    }

    @Override
    public ProductRating updateRating(ProductRating existing, ProductRating updated) {
        existing.setRatings(updated.getRatings());
        existing.setFeedback(updated.getFeedback());
        return productRatingRepository.save(existing);
    }

    @Override
    public void deleteRating(ProductRating rating) {
        productRatingRepository.delete(rating);
    }

    @Override
    public List<ProductRating> getRatingsByProductId(Long productId) {
        return productRatingRepository.findByProductId(productId);
    }
}

