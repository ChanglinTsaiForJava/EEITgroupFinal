package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.Progress;
import eeit.OldProject.yuni.Repository.ProgressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressService{
    @Autowired
    private ProgressRepository progressRepository;
}
