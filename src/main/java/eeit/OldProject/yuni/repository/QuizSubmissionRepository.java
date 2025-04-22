package eeit.OldProject.yuni.repository;

import eeit.OldProject.yuni.entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Integer> {
}
