package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.Favorite_employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Favorite_empRepository extends JpaRepository<Favorite_employee, Long> {
    // 根據使用者和照顧者ID查詢收藏
    Optional<Favorite_employee> findByUserIdAndCaregiverId(Long userId, Long caregiverId);

    // 根據使用者ID查詢所有收藏員工
    List<Favorite_employee> findByUserId(Long userId);
}
