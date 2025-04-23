package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService{

    @Autowired
    private CourseRepository courseRepository;
}
