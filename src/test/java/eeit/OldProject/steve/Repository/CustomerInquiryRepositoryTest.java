package eeit.OldProject.steve.Repository;

import eeit.OldProject.steve.Entity.CustomerInquiry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerInquiryRepositoryTest {

    @Autowired
    private CustomerInquiryRepository customerInquiryRepository;

    @Test
    void testSaveCustomerInquiry() {
        CustomerInquiry inquiry = new CustomerInquiry();
        inquiry.setUserId(1);
        inquiry.setCaregiver(2);
        inquiry.setEmail("test@example.com");
        inquiry.setInquiryText("I need help with my order.");
        inquiry.setCreatedDate(LocalDateTime.now());
        inquiry.setStatus("OPEN");
        inquiry.setResponseText(null);
        inquiry.setResponseDate(null);

        CustomerInquiry saved = customerInquiryRepository.save(inquiry);

        assertNotNull(saved.getInquiryId());
        assertEquals("OPEN", saved.getStatus());
    }

    @Test
    void testFindById() {
        CustomerInquiry inquiry = new CustomerInquiry();
        inquiry.setUserId(2);
        inquiry.setCaregiver(3);
        inquiry.setEmail("find@example.com");
        inquiry.setInquiryText("Where is my item?");
        inquiry.setCreatedDate(LocalDateTime.now());
        inquiry.setStatus("PENDING");

        CustomerInquiry saved = customerInquiryRepository.save(inquiry);

        Optional<CustomerInquiry> found = customerInquiryRepository.findById(Long.valueOf(saved.getInquiryId()));
        assertTrue(found.isPresent());
        assertEquals("PENDING", found.get().getStatus());
    }

    @Test
    void testFindAll() {
        CustomerInquiry inquiry1 = new CustomerInquiry();
        inquiry1.setUserId(3);
        inquiry1.setCaregiver(4);
        inquiry1.setEmail("all1@example.com");
        inquiry1.setInquiryText("Test inquiry 1");
        inquiry1.setCreatedDate(LocalDateTime.now());
        inquiry1.setStatus("OPEN");

        CustomerInquiry inquiry2 = new CustomerInquiry();
        inquiry2.setUserId(4);
        inquiry2.setCaregiver(5);
        inquiry2.setEmail("all2@example.com");
        inquiry2.setInquiryText("Test inquiry 2");
        inquiry2.setCreatedDate(LocalDateTime.now());
        inquiry2.setStatus("CLOSED");

        customerInquiryRepository.save(inquiry1);
        customerInquiryRepository.save(inquiry2);

        List<CustomerInquiry> all = customerInquiryRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testDeleteById() {
        CustomerInquiry inquiry = new CustomerInquiry();
        inquiry.setUserId(5);
        inquiry.setCaregiver(6);
        inquiry.setEmail("delete@example.com");
        inquiry.setInquiryText("Please delete me.");
        inquiry.setCreatedDate(LocalDateTime.now());
        inquiry.setStatus("DELETE");

        CustomerInquiry saved = customerInquiryRepository.save(inquiry);
        Integer id = saved.getInquiryId();

        customerInquiryRepository.deleteById(Long.valueOf(id));

        Optional<CustomerInquiry> deleted = customerInquiryRepository.findById(Long.valueOf(id));
        assertFalse(deleted.isPresent());
    }
}
