package eeit.OldProject.yuni.Service;


import eeit.OldProject.yuni.Entity.Record;
import eeit.OldProject.yuni.Repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RecordService{
    @Autowired
    private RecordRepository recordRepository;
}
