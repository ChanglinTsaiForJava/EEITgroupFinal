package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.QuizAnswerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizAnswerRecordService{
    @Autowired
    private QuizAnswerRecordRepository quizAnswerRecordRepository;
}
