package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.QuizSubmission;
import eeit.OldProject.yuni.repository.QuizSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizSubmissionService{

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
}
