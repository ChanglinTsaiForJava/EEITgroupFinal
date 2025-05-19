package eeit.OldProject.steve.Controller;


import eeit.OldProject.steve.Repository.UserRepository;
import eeit.OldProject.steve.Entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // 登入方法1.1
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User requestUser, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUserAccount(requestUser.getUserAccount());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUserPassword().equals(requestUser.getUserPassword())) {
                session.setAttribute("userId", user.getUserId());
                return ResponseEntity.ok("登入成功");
                // 登入成功，這裡會建立 Session 並儲存 userId，將 userId 存入 session
            } else {
                return ResponseEntity.badRequest().body("帳號或是密碼錯誤");
            }
        } else {
            return ResponseEntity.badRequest().body("帳號或是密碼錯誤 ");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // 移除 session 裡的 userId
        session.invalidate(); // 這行會直接整個 session 清除
        return ResponseEntity.ok("登出成功");
    }
}


