package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.Favorite_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Favorite_courseRepository extends JpaRepository<Favorite_course, Long> {

}
