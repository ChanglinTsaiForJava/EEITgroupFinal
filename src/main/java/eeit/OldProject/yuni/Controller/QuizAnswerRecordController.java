package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.QuizAnswerRecordService;

@Controller
public class QuizAnswerRecordController{
    @Autowired
    private QuizAnswerRecordService quizAnswerRecordService;
}
