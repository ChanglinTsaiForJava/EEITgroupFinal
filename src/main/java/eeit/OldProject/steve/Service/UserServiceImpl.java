package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailSender;

    // 更新使用者資料
    @Override
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // 僅允許更新以下欄位，密碼請走驗證信流程更新
            // 只更新有傳遞過來的欄位
            if (updatedUser.getUserName() != null) {
                user.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getEmailAddress() != null) {
                user.setEmailAddress(updatedUser.getEmailAddress());
            }
            if (updatedUser.getPhoneNumber() != null) {
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (updatedUser.getAddress() != null) {
                user.setAddress(updatedUser.getAddress());
            }
            if (updatedUser.getProfilePicture() != null) {
                user.setProfilePicture(updatedUser.getProfilePicture());
            }
            if (updatedUser.getBio() != null) {
                user.setBio(updatedUser.getBio());
            }
            if (updatedUser.getIntro() != null) {
                user.setIntro(updatedUser.getIntro());
            }

            return userRepository.save(user);
        }
        return null;
    }

    // 處理圖片上傳並返回圖片 URL
//    @Override
//    public String uploadProfilePicture(Long userId, MultipartFile profilePicture) throws IOException {
//        // 假設圖片儲存在本地資料夾中，你可以選擇儲存在雲端服務
//        String uploadDir = "/Users/steve/Documents/intellij/OldProject/uploads/profile_pictures/";
//        Path uploadPath = Paths.get(uploadDir);
//
//        // 如果資料夾不存在則創建
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath); // 這會創建所有必要的資料夾
//        }
//
//        // 獲取圖片檔案名稱並儲存圖片
//        String fileName = userId + "_" + profilePicture.getOriginalFilename();
//        Path filePath = uploadPath.resolve(fileName);
//        profilePicture.transferTo(filePath.toFile());
//
//        // 假設你儲存圖片後，返回圖片 URL，這裡只是個範例
//        String imageUrl = "http://localhost:8082/" + uploadDir + fileName;
//
//        // 更新使用者資料庫中的圖片 URL
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setProfilePicture(imageUrl);
//            userRepository.save(user);
//        }
//
//        return imageUrl;
//    }
    // 查詢使用者資料
    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);  // 若找不到使用者則返回 null
    }

    // 更新使用者圖片 URL
//    @Override
//    public boolean updateProfilePicture(Long userId, String profilePictureUrl) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setProfilePicture(profilePictureUrl);
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }



    @Override
    public ResponseEntity<?> sendPasswordResetVerification(String emailAddress) {
        Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("此 Email 尚未註冊");
        }

        // 產生 6 碼驗證碼
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        // 寄出 Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("密碼重設驗證碼");
        message.setText("您的驗證碼為：" + code + "\n請於 10 分鐘內完成重設密碼。");

        mailSender.send(message);

        // 將驗證碼暫存到 session（或 Redis）
        session.setAttribute("verify_code_" + emailAddress, code);

        return ResponseEntity.ok("驗證碼已寄出，請查收信箱");
    }


    //忘記密碼
    @Override
    public ResponseEntity<?> resetPasswordWithVerification(String emailAddress, String code, String newPassword) {
        Object sessionCode = session.getAttribute("verify_code_" + emailAddress);
        if (sessionCode == null || !sessionCode.toString().equals(code)) {
            return ResponseEntity.status(400).body("驗證碼錯誤或已過期");
        }

        Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("找不到帳號");
        }

        User user = optionalUser.get();
        user.setUserPassword(newPassword); // 這裡可加入密碼加密（推薦）
        userRepository.save(user);

        // 清除驗證碼
        session.removeAttribute("verify_code_" + emailAddress);

        return ResponseEntity.ok("密碼已重設");
    }

    @Override
    public ResponseEntity<?> changePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("找不到使用者");
        }

        User user = optionalUser.get();

        // 這裡建議做加密驗證，如果你有用 BCrypt 的話
        if (!user.getUserPassword().equals(currentPassword)) {
            return ResponseEntity.status(403).body("原密碼錯誤");
        }

        user.setUserPassword(newPassword); // 建議這裡加密
        userRepository.save(user);

        return ResponseEntity.ok("密碼修改成功");
    }
    @Override
    public void updateUserProfilePicture(Long userId, byte[] imageBytes) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfilePicture(imageBytes);
            userRepository.save(user);
        }
    }

    @Override
    public byte[] getUserProfilePicture(Long userId) {
        return userRepository.findById(userId)
                .map(User::getProfilePicture)
                .orElse(null);
    }

}
