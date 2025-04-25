package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.QuizOption;
import eeit.OldProject.yuni.Repository.QuizOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class QuizOptionService{

    @Autowired
    private QuizOptionRepository quizOptionRepository;
}
