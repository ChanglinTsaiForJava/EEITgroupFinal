package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.QuizQuestion;
import eeit.OldProject.yuni.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService{
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
}
