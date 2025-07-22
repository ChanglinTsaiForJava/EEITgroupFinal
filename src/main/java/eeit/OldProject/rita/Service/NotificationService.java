package eeit.OldProject.rita.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * ✅ NotificationService：寄送 HTML 格式 Email
 *    通知使用者預約成功
 */

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    // 建構子注入 JavaMailSender
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom("ririhung11@gmail.com");

            mailSender.send(message);
            System.out.println("📬 已寄出 HTML Email 給：" + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("寄送 Email 失敗", e);
        }
}}
