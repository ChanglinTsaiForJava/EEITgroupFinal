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

    // 登入方法
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User requestUser, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUserAccount(requestUser.getUserAccount());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUserPassword().equals(requestUser.getUserPassword())) {
                // 登入成功，將 userId 存入 session
                session.setAttribute("userId", user.getUserId());
                return ResponseEntity.ok("登入成功");
            } else {
                return ResponseEntity.badRequest().body("密碼錯誤");
            }
        } else {
            return ResponseEntity.badRequest().body("帳號不存在");
        }
    }
}


