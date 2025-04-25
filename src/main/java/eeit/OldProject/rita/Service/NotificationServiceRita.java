package eeit.OldProject.rita.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceRita {

    private final JavaMailSender mailSender;

    public NotificationServiceRita(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        mail.setFrom("your_email@example.com"); // 記得改信箱

        mailSender.send(mail);
        System.out.println("📬 已寄出 Email 給：" + to);

    }
}
