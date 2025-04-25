package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.QuizQuestion;
import eeit.OldProject.yuni.Repository.QuizQuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService{
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
}
