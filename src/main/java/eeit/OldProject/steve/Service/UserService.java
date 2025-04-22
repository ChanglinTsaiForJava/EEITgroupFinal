package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    User updateUser(Long userId, User updatedUser);

    boolean updateProfilePicture(Long userId, String profilePictureUrl);

    String uploadProfilePicture(Long userId, MultipartFile profilePicture) throws IOException; // 添加 throws IOException
    // 其他需要的方法可以在這裡定義

}


