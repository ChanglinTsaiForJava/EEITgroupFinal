package eeit.OldProject.yuuhou.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.RequestDTO.RegisterRequest;

public interface CaregiversService {

    List<Caregiver> findAll();

    Optional<Caregiver> findById(Long id);

    Caregiver save(Caregiver caregiver);

    void deleteById(Long id);

    Optional<Caregiver> findByEmail(String email);
    
    List<Caregiver> searchByServiceArea(String serviceCity, String serviceDistrict);

    void cacheRegistrationData(RegisterRequest request);
    
    RegisterRequest getCachedRegistrationData(String email);

    void removeCachedRegistrationData(String email);
    
    void saveVerificationCode(String email, String verificationCode, LocalDateTime expiresAt);
    
    boolean verifyCode(String email, String inputCode);
    
    boolean isVerificationCodeExpired(String email);
}
