package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.QuizOption;
import eeit.OldProject.yuni.repository.QuizOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizOptionService{

    @Autowired
    private QuizOptionRepository quizOptionRepository;
}
