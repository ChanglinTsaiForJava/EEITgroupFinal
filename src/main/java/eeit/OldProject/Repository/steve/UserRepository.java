package eeit.OldProject.Repository.steve;

import eeit.OldProject.entity.steve.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAccount(String userAccount);
    boolean existsByUserAccount(String userAccount);
}