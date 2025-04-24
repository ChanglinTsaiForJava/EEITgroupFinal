package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Repository.ChapterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ChapterService{

    @Autowired
    private ChapterRepository chapterRepository;
}
