package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService{
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
}
