package eeit.OldProject.yuni.repository;

import eeit.OldProject.yuni.entity.QuizAnswerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAnswerRecordRepository extends JpaRepository<QuizAnswerRecord, Integer> {
}
