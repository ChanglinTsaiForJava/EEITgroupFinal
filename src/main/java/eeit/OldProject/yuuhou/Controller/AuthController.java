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
            return ResponseEntity.badRequest().body("此 Email 已被註冊！");
        }
        


        // 建立照顧者（先 isVerified=false）
        Caregiver caregiver = Caregiver.builder()
                .caregiverName(request.getCaregiverName())
                .gender(request.getGender())
                .birthday(request.getBirthday())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .nationality(request.getNationality())
                .languages(request.getLanguages())
                .yearOfExperience(request.getYearOfExperience())
                .serviceCity(request.getServiceCity())
                .serviceDistrict(request.getServiceDistrict())
                .description(request.getDescription())
                .hourlyRate(request.getHourlyRate())
                .halfDayRate(request.getHalfDayRate())
                .fullDayRate(request.getFullDayRate())
                .status(Caregiver.Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .isVerified(false) // ✅ 設成未驗證
                .build();
        if (request.getBase64Photo() != null && !request.getBase64Photo().isEmpty()) {
            try {
                caregiver.setPhoto(java.util.Base64.getDecoder().decode(request.getBase64Photo()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("圖片格式錯誤，無法解析 Base64！");
            }
        }


        caregiversService.save(caregiver);

        // ✅ 產生驗證Token
        String token = UUID.randomUUID().toString();
        verificationTokens.put(token, caregiver.getEmail());

        // ✅ 發送驗證信
        String verificationUrl = "http://localhost:8082/api/auth/verify?token=" + token;
        sendVerificationEmail(caregiver.getEmail(), verificationUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功！請至信箱點擊連結完成驗證！");
    }
    
    private void sendVerificationEmail(String to, String verificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("帳號驗證信 - OldProject平台");
        message.setText("歡迎您註冊！請點擊以下連結完成驗證：\n" + verificationUrl);
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
        String resetUrl = "http://localhost:5173/reset?token=" + token; // 這是給前端用的連結

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
    
//    @PostMapping("/api/caregivers/photo")
//    public ResponseEntity<?> uploadPhoto(@RequestPart("file") MultipartFile file,
//                                         Authentication authentication) {
//        String email = authentication.getName();
//        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
//
//        if (caregiverOpt.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("找不到使用者");
//        }
//
//        Caregiver caregiver = caregiverOpt.get();
//
//        try {
//            // 存檔案到 static/images 資料夾（先確認有此資料夾）
//            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
//            Path path = Paths.get("src/main/resources/static/yuuhou/images/" + filename);
//            Files.createDirectories(path.getParent()); // 若目錄不存在則建立
//            Files.write(path, file.getBytes());
//
//            // 設定路徑到資料庫
//            caregiver.setPhotoPath("/yuuhou/images/" + filename);
//            caregiversService.save(caregiver);
//
//            return ResponseEntity.ok(Collections.singletonMap("photoPath", caregiver.getPhotoPath()));
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上傳失敗");
//        }
//    }

    

    
    
}
