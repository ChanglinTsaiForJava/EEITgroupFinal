package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService{
    @Autowired
    private ProgressRepository progressRepository;
}
