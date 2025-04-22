package eeit.OldProject.yuni.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eeit.OldProject.yuni.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
}
