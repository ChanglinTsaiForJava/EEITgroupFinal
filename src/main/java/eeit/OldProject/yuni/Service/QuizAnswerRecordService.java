package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.QuizAnswerRecord;
import eeit.OldProject.yuni.Repository.QuizAnswerRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizAnswerRecordService{
    @Autowired
    private QuizAnswerRecordRepository quizAnswerRecordRepository;
}
