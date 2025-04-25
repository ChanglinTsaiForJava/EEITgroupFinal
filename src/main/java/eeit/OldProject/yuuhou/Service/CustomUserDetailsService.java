package eeit.OldProject.yuuhou.Service;



import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CaregiversRepository caregiverRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ✅ 超級使用者直接寫死
        if (username.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password("{noop}admin123") // {noop} 是不加密的意思（開發用）
                    .roles("ADMIN")
                    .build();
        }

        // ✅ 從資料庫查照顧者帳號
        Optional<Caregiver> optional = caregiverRepo.findByEmail(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("找不到使用者: " + username);
        }

        Caregiver caregiver = optional.get();

        return User.builder()
                .username(caregiver.getEmail())
                .password(caregiver.getPassword()) // 記得要加密！
                .roles("CAREGIVER")
                .build();
    }
}
