package eeit.OldProject.yuni.repository;

import eeit.OldProject.yuni.entity.Certificate;
import eeit.OldProject.yuni.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
}
