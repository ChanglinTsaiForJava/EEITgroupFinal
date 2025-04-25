package eeit.OldProject.allen.Repository;

import eeit.OldProject.allen.Entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    
}
