package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.QuizSubmission;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Integer> {
}
