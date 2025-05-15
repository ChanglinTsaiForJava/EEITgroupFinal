package eeit.OldProject.yuuhou.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.RequestDTO.LoginRequest;
import eeit.OldProject.yuuhou.RequestDTO.RegisterRequest;
import eeit.OldProject.yuuhou.Service.CaregiversService;
import eeit.OldProject.yuuhou.Util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CaregiversService caregiversService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private JavaMailSender mailSender; // ✅ 用來寄信

    // 暫存 token 對應 email (Memory版)
    private Map<String, String> verificationTokens = new ConcurrentHashMap<>();


    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        boolean exists = caregiversService.findByEmail(email).isPresent();
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 檢查 Email 是否已存在
        if (caregiversService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("❌ 此 Email 已被註冊！");
        }

        // ✅ 檢查是否有上傳大頭貼
        if (request.getBase64Photo() == null || request.getBase64Photo().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ 必須上傳大頭貼！");
        }

        // ✅ 產生驗證碼
        String verificationCode = String.valueOf((int)(Math.random() * 900000) + 100000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);
        caregiversService.saveVerificationCode(request.getEmail(), verificationCode, expiresAt);

        // ✅ 暫存註冊資料（不直接存入資料庫）
        caregiversService.cacheRegistrationData(request);

        // ✅ 發送驗證碼
        sendVerificationCodeEmail(request.getEmail(), verificationCode);

        return ResponseEntity.ok("✅ 驗證碼已發送到您的信箱，請輸入驗證碼完成註冊！");
    }



    
    private void sendVerificationCodeEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("🔒 驗證碼 - OldProject平台");
        message.setText("您的驗證碼是：" + verificationCode + "\n請在 10 分鐘內使用。");
        mailSender.send(message);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // ✅ 1. 先特別處理 超級使用者 admin
        if (email.equals("admin") && password.equals("admin123")) {
            String token = jwtUtil.generateToken("admin", "ADMIN");
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        }

        // ✅ 2. 再查詢一般照顧者資料表
        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號錯誤！");
        }

        Caregiver caregiver = caregiverOpt.get();

        // ✅ 3. 檢查是否完成信箱驗證
        if (!caregiver.isVerified()) {
        	System.out.println("信箱認證確認");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ 請先完成信箱驗證！");
        }

        // ✅ 4. 比對密碼
        if (!password.equals(caregiver.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密碼錯誤！");
        }


        // ✅ 5. 都OK，回傳 CAREGIVER 身份的token
        String token = jwtUtil.generateToken(caregiver.getEmail(), "CAREGIVER");

        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

    
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        String email = verificationTokens.get(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 無效或過期的驗證連結！");
        }

        // 找到照顧者
        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 找不到對應帳號！");
        }

        Caregiver caregiver = caregiverOpt.get();

        // 將 isVerified 改成 true
        caregiver.setVerified(true);
        caregiversService.save(caregiver);

        // 驗證完刪除 token，避免重複使用
        verificationTokens.remove(token);

        return ResponseEntity.ok("✅ 驗證成功！您的帳號已啟用，可以登入囉！");
    }

 // 忘記密碼 (發送驗證信)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
        if (caregiverOpt.isEmpty()) {
            // 不要告訴使用者 email 有沒有存在（安全）
            return ResponseEntity.ok("如果該信箱存在，我們已寄出重設密碼信件！");
        }

        // 產生新的 token
        String token = UUID.randomUUID().toString();
        verificationTokens.put(token, email);

        // 建立重設密碼的連結
        String resetUrl = "http://192.168.66.118:4173/reset/yuuhou?token=" + token; // 這是給前端用的連結

        // 寄出 email	
        sendResetPasswordEmail(email, resetUrl);

        return ResponseEntity.ok("如果該信箱存在，我們已寄出重設密碼信件！");
    }

    // 寄送重設密碼的 Email
 // 寄送重設密碼的 Email
    private void sendResetPasswordEmail(String to, String resetUrl) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("🔒 重設密碼 - CarePlus平台");

            // 📝 Email 內容 (HTML 格式)
            String htmlContent = "<h2>重設密碼</h2>" +
                    "<p>請點擊以下連結重新設定您的密碼：</p>" +
                    "<a href=\"" + resetUrl + "\" target=\"_blank\">" + resetUrl + "</a>" ;
                    

            helper.setText(htmlContent, true);  // ✅ 設定 HTML 內容
            mailSender.send(message);
            System.out.println("✅ 重設密碼信件已發送：" + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("❌ 寄信失敗：" + e.getMessage());
        }
    }
    // 寄送重設密碼的 Email
//  private void sendResetPasswordEmail(String to, String resetUrl) {
//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(to);
//         message.setSubject("重設密碼 - OldProject平台");
//         message.setText("請點擊以下連結重新設定您的密碼：\n" + resetUrl);
//         mailSender.send(message);

    // 重設密碼 (用token驗證)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        String email = verificationTokens.get(token);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 無效或過期的重設連結！");
        }

        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 找不到對應帳號！");
        }

        Caregiver caregiver = caregiverOpt.get();
        caregiver.setPassword(newPassword);

        caregiversService.save(caregiver);

        // 用完後移除 token
        verificationTokens.remove(token);

        return ResponseEntity.ok("✅ 密碼重設成功！可以使用新密碼登入了！");
    }
    
    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String inputCode = request.get("verificationCode");

        // ✅ 檢查驗證碼是否過期
        if (caregiversService.isVerificationCodeExpired(email)) {
            caregiversService.removeCachedRegistrationData(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 驗證碼已過期，請重新註冊！");
        }

        // ✅ 檢查驗證碼是否正確
        if (!caregiversService.verifyCode(email, inputCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 驗證碼錯誤！");
        }

        // ✅ 讀取暫存的註冊資料
        RegisterRequest cachedRequest = caregiversService.getCachedRegistrationData(email);
        if (cachedRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 註冊資料已過期或不存在！");
        }

        // ✅ 將註冊資料存入資料庫
        Caregiver caregiver = Caregiver.builder()
                .caregiverName(cachedRequest.getCaregiverName())
                .gender(cachedRequest.getGender())
                .birthday(cachedRequest.getBirthday())
                .email(cachedRequest.getEmail())
                .password(cachedRequest.getPassword())
                .phone(cachedRequest.getPhone())
                .nationality(cachedRequest.getNationality())
                .languages(cachedRequest.getLanguages())
                .yearOfExperience(cachedRequest.getYearOfExperience())
                .serviceCity(cachedRequest.getServiceCity())
                .serviceDistrict(cachedRequest.getServiceDistrict())
                .description(cachedRequest.getDescription())
                .hourlyRate(cachedRequest.getHourlyRate())
                .halfDayRate(cachedRequest.getHalfDayRate())
                .fullDayRate(cachedRequest.getFullDayRate())
                .photo(java.util.Base64.getDecoder().decode(cachedRequest.getBase64Photo()))
                .status(Caregiver.Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .isVerified(true)  // ✅ 設為已驗證
                .build();

        caregiversService.save(caregiver);

        // ✅ 驗證成功後清除暫存資料
        caregiversService.removeCachedRegistrationData(email);

        return ResponseEntity.ok("✅ 驗證成功！您的帳號已啟用，可以登入囉！");
    }


    

    
    
}
