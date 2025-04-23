package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.QuizSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizSubmissionService{

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
}
