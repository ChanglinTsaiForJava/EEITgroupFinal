package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.Favorite_employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Favorite_empRepository extends JpaRepository<Favorite_employee, Long> {
}
