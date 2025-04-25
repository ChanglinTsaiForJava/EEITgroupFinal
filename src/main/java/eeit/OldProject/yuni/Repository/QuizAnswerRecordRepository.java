package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.QuizAnswerRecord;

@Repository
public interface QuizAnswerRecordRepository extends JpaRepository<QuizAnswerRecord, Integer> {
}
