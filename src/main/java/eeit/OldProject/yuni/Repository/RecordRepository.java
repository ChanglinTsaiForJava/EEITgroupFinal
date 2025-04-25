package eeit.OldProject.yuni.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
}
