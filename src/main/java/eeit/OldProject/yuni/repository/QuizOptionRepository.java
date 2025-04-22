package eeit.OldProject.yuni.repository;

import eeit.OldProject.yuni.entity.QuizOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizOptionRepository extends JpaRepository<QuizOption, Integer> {
}
