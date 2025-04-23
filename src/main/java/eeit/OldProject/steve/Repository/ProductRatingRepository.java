package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    List<ProductRating> findByProductId(Long productId);
}
