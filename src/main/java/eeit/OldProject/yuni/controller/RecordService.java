package eeit.OldProject.yuni.controller;


import eeit.OldProject.yuni.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService{
    @Autowired
    private RecordRepository recordRepository;
}
