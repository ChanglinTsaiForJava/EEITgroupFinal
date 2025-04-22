package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 編輯使用者資訊
    @PutMapping("/edit")
    public ResponseEntity<?> editUserInfo(@RequestBody User updatedUser, HttpSession session) {
        // 從 session 取得已登入的 userId
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("尚未登入");
        }

        // 更新使用者資料
        User user = userService.updateUser(userId, updatedUser);
        if (user == null) {
            return ResponseEntity.status(404).body("使用者不存在");
        }

        return ResponseEntity.ok("使用者資訊更新成功");
    }
}
