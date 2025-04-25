package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 查詢當前使用者的所有資料
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpSession session) {
        // 從 session 取得已登入的 userId
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("尚未登入");
        }

        // 查詢使用者資料
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("使用者不存在");
        }

        return ResponseEntity.ok(user);
    }
    // 編輯使用者資訊
    @PutMapping("/edit")
    public ResponseEntity<?> editUserInfo(@RequestBody User updatedUser, HttpSession session) {
        // 從 session 取得已登入的 userId
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("尚未登入");
        }

        // 更新使用者資料，包含 ProfilePicture URL
        User user = userService.updateUser(userId, updatedUser);
        if (user == null) {
            return ResponseEntity.status(404).body("使用者不存在");
        }

        return ResponseEntity.ok("使用者資訊更新成功");
    }

    // 編輯使用者圖片 URL
    @PutMapping("/edit-profile-picture")
    public ResponseEntity<?> editProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, HttpSession session) throws IOException {
        // 從 session 取得已登入的 userId
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("尚未登入");
        }

        // 上傳圖片並取得 URL
        String imageUrl = userService.uploadProfilePicture(userId, profilePicture);
        if (imageUrl == null) {
            return ResponseEntity.status(500).body("圖片上傳失敗");
        }

        return ResponseEntity.ok("使用者圖片更新成功，圖片 URL: " + imageUrl);
    }
    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String emailAddress) {
        return userService.sendPasswordResetVerification(emailAddress);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String emailAddress,
                                           @RequestParam String verificationCode,
                                           @RequestParam String newPassword) {
        return userService.resetPasswordWithVerification(emailAddress, verificationCode, newPassword);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String currentPassword,
                                            @RequestParam String newPassword,
                                            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("尚未登入");
        }

        return userService.changePassword(userId, currentPassword, newPassword);
    }
}