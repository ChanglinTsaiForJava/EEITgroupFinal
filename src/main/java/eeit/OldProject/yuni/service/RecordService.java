package eeit.OldProject.yuni.service;


import eeit.OldProject.yuni.entity.Record;
import eeit.OldProject.yuni.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RecordService{
    @Autowired
    private RecordRepository recordRepository;
}
