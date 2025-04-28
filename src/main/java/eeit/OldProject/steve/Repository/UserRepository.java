package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAccount(String userAccount);
    boolean existsByUserAccount(String userAccount);
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
}