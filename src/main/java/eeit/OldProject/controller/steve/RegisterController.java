package eeit.OldProject.controller.steve;


import eeit.OldProject.Repository.steve.UserRepository;
import eeit.OldProject.entity.steve.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class RegisterController {


    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User requestUser) {

//        if (userRepository.existsByUserAccount(requestUser.getUserAccount())) {
//            return ResponseEntity.badRequest().body("帳號已存在");
//        }

        // 建立一個新的 User，只填需要的欄位
        System.out.println(requestUser.toString());
        User newUser = new User();
        newUser.setUserAccount(requestUser.getUserAccount());
        newUser.setUserPassword(requestUser.getUserPassword());
        newUser.setUserName(requestUser.getUserName());
        newUser.setEmailAddress(requestUser.getEmailAddress());
        newUser.setCreatedAt(LocalDateTime.now()); // 自動加上建立時間
        System.out.println(requestUser.toString());


        return ResponseEntity.ok( userRepository.save(newUser));
    }
}

