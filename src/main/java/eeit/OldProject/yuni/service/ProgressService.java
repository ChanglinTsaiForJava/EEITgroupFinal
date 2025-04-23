package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.Progress;
import eeit.OldProject.yuni.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressService{
    @Autowired
    private ProgressRepository progressRepository;
}
