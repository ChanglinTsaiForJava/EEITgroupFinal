package eeit.OldProject.steve.Controller;


import eeit.OldProject.steve.Entity.CustomerInquiry;
import eeit.OldProject.steve.Repository.CustomerInquiryRepository;
import eeit.OldProject.steve.Service.CustomerInquiryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/inquiry")
public class CustomerInquiryController {

    @Autowired
    private CustomerInquiryService inquiryService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitInquiry(@RequestBody CustomerInquiry inquiry, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("請先登入");
        }

        inquiry.setUserId(userId);
        inquiry.setCreatedDate(LocalDateTime.now());
        inquiry.setStatus("未回覆");

        inquiryService.submitInquiryAndSendEmail(inquiry);
        return ResponseEntity.ok("已成功送出詢問");
    }
}
