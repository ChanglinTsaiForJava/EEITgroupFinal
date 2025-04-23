package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterService{

    @Autowired
    private ChapterRepository chapterRepository;
}
