package eeit.OldProject.steve.Controller;


import eeit.OldProject.steve.DTO.RegisterRequestDTO;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import eeit.OldProject.steve.Service.UserService;
import eeit.OldProject.steve.DTO.PendingUser;
import eeit.OldProject.steve.DTO.VerificationStorage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationStorage verificationStorage;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        boolean exists = userRepository.findByEmailAddressIgnoreCase(email).isPresent();
        return ResponseEntity.ok(exists); // true = 已存在, false = 可用
    }
    @PostMapping("/register/send")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody @Valid RegisterRequestDTO requestDTO) {
        if (userRepository.existsByUserAccount(requestDTO.getUserAccount())) {
            return ResponseEntity.badRequest().body("帳號已存在");
        }

        // 🔥 加這段：檢查 Email（大小寫不敏感）
        if (userRepository.findByEmailAddressIgnoreCase(requestDTO.getEmailAddress()).isPresent()) {
            return ResponseEntity.badRequest().body("信箱已存在");
        }

        // 建立一個 User 實體（尚未存入 DB）
        User user = new User();
        user.setUserAccount(requestDTO.getUserAccount());
        user.setUserPassword(requestDTO.getUserPassword());
        user.setUserName(requestDTO.getUserName());
        user.setEmailAddress(requestDTO.getEmailAddress());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        user.setAddress(requestDTO.getAddress());
        //set timer
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        PendingUser pendingUser = new PendingUser(user, code, LocalDateTime.now().plusMinutes(15));
        verificationStorage.save(user.getEmailAddress(), pendingUser);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmailAddress());
        message.setSubject("註冊驗證碼");
        message.setText("您的驗證碼是：" + code + "，請在 15 分鐘內完成註冊。");
        mailSender.send(message);

        return ResponseEntity.ok("驗證碼已寄出，請查收信箱");
    }

    @PostMapping("/register/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        PendingUser pending = verificationStorage.get(email);
        if (pending == null || pending.isExpired()) {
            return ResponseEntity.badRequest().body("驗證碼已過期或無效");
        }

        if (!pending.getVerificationCode().equals(code)) {
            return ResponseEntity.badRequest().body("驗證碼錯誤");
        }

        User newUser = pending.getUser();
        newUser.setCreatedAt(LocalDateTime.now());
        userService.createUser(newUser);
        verificationStorage.remove(email);

        return ResponseEntity.ok("註冊成功！");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("資料格式錯誤");
        return ResponseEntity.badRequest().body(errorMessage);
    }
}

