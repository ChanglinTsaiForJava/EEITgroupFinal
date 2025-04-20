package eeit.OldProject.Repository;

import eeit.OldProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAccount(String userAccount);
    boolean existsByUserAccount(String userAccount);
}