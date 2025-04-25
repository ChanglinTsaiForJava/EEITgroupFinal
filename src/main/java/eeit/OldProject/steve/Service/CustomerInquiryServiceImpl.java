package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.CustomerInquiry;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.CustomerInquiryRepository;
import eeit.OldProject.steve.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerInquiryServiceImpl implements CustomerInquiryService {

    @Autowired
    private CustomerInquiryRepository inquiryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final String ADMIN_EMAIL = "changlin.stevetsai@gmail.com";

    @Override
    public void submitInquiryAndSendEmail(CustomerInquiry inquiry) {
        inquiryRepository.save(inquiry);

        // 找出使用者 email
        Optional<User> optionalUser = userRepository.findById(inquiry.getUserId());
        if (optionalUser.isEmpty()) return;

        String userEmail = optionalUser.get().getEmailAddress();

        // 組裝 email 內容
        String subject = "客服詢問 - 來自 " + userEmail;
        StringBuilder content = new StringBuilder();
        content.append("用戶 ID: ").append(inquiry.getUserId()).append("\n");
        content.append("Email: ").append(userEmail).append("\n");
        if (inquiry.getCaregiverId() != null) {
            content.append("照護者 ID: ").append(inquiry.getCaregiverId()).append("\n");
        }
        content.append("問題內容:\n").append(inquiry.getInquiryText());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ADMIN_EMAIL);
        message.setSubject(subject);
        message.setText(content.toString());

        mailSender.send(message);
    }
}
