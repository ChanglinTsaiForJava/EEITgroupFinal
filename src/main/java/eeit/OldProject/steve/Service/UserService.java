package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
    User updateUser(Long userId, User updatedUser);

//    boolean updateProfilePicture(Long userId, String profilePictureUrl);
//
//    String uploadProfilePicture(Long userId, MultipartFile profilePicture) throws IOException; // 添加 throws IOException

    User getUserById(Long userId);
    // 其他需要的方法可以在這裡定義
    User createUser(User user);


    ResponseEntity<?> sendPasswordResetVerification(String emailAddress);

    ResponseEntity<?> resetPasswordWithVerification(String emailAddress, String code, String newPassword);

    ResponseEntity<?> changePassword(Long userId, String currentPassword, String newPassword);
    void updateUserProfilePicture(Long userId, byte[] imageBytes);
    byte[] getUserProfilePicture(Long userId);
}


