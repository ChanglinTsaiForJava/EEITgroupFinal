package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.Favorite_product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Favorite_productRepository extends JpaRepository<Favorite_product, Long> {
    // 根據使用者和產品ID查詢收藏
    Optional<Favorite_product> findByUserIdAndProductId(Long userId, Long productId);

    // 根據使用者ID查詢所有收藏產品
    List<Favorite_product> findByUserId(Long userId);
}
