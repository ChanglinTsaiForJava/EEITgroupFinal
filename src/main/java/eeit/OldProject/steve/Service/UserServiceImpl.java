package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // 更新使用者資料
    @Override
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

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
}
