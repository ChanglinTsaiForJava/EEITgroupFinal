package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.QuizQuestionService;

@Controller
public class QuizQuestionController{
    @Autowired
    private QuizQuestionService quizQuestionService;
}
