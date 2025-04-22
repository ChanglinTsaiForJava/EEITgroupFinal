package eeit.OldProject.steve.Service;


import eeit.OldProject.steve.Entity.User;

import java.util.Optional;

public interface UserService {
    User updateUser(Long userId, User updatedUser);
    // 其他需要的方法可以在這裡定義
}

