package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.QuizSubmissionService;

@Controller
public class QuizSubmissionController{

    @Autowired
    private QuizSubmissionService quizSubmissionService;
}
