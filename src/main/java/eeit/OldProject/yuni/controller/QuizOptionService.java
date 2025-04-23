package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.QuizOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizOptionService{

    @Autowired
    private QuizOptionRepository quizOptionRepository;
}
