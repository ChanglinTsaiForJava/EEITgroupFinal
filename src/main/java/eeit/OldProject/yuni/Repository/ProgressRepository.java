package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.yuni.Entity.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
}
