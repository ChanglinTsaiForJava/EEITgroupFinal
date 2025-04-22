package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {

}
