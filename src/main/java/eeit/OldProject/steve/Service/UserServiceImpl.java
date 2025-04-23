package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    // 處理圖片上傳並返回圖片 URL
    @Override
    public String uploadProfilePicture(Long userId, MultipartFile profilePicture) throws IOException {
        // 假設圖片儲存在本地資料夾中，你可以選擇儲存在雲端服務
        String uploadDir = "/Users/steve/Documents/intellij/OldProject/uploads/profile_pictures/";
        Path uploadPath = Paths.get(uploadDir);

        // 如果資料夾不存在則創建
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // 這會創建所有必要的資料夾
        }

        // 獲取圖片檔案名稱並儲存圖片
        String fileName = userId + "_" + profilePicture.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        profilePicture.transferTo(filePath.toFile());

        // 假設你儲存圖片後，返回圖片 URL，這裡只是個範例
        String imageUrl = "http://localhost:8082/" + uploadDir + fileName;

        // 更新使用者資料庫中的圖片 URL
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePicture(imageUrl);
            userRepository.save(user);
        }

        return imageUrl;
    }
    // 查詢使用者資料
    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);  // 若找不到使用者則返回 null
    }

    // 更新使用者圖片 URL
    @Override
    public boolean updateProfilePicture(Long userId, String profilePictureUrl) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfilePicture(profilePictureUrl);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
