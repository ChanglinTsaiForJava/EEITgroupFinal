package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.Chapter;
import eeit.OldProject.yuni.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ChapterService{

    @Autowired
    private ChapterRepository chapterRepository;
}
