package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.QuizSubmission;
import eeit.OldProject.yuni.Repository.QuizSubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizSubmissionService{

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
}
