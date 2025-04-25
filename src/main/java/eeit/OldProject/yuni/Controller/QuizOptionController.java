package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.QuizOptionService;

@Controller
public class QuizOptionController{

    @Autowired
    private QuizOptionService quizOptionService;
}
