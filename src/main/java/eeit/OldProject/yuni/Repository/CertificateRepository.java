package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.Certificate;

@Repository
public interface CertificateRepository  extends JpaRepository<Certificate, Integer> {
}
