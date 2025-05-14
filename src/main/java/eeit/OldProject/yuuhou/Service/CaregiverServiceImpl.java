package eeit.OldProject.yuuhou.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import eeit.OldProject.yuuhou.RequestDTO.RegisterRequest;

@Service
public class CaregiverServiceImpl implements CaregiversService {

    @Autowired
    private CaregiversRepository repository;

    // 暫存註冊資料
    private final Map<String, RegisterRequest> registrationCache = new ConcurrentHashMap<>();

    // 暫存驗證碼
    private final Map<String, String> verificationCodeCache = new ConcurrentHashMap<>();

    // 驗證碼過期時間
    private final Map<String, LocalDateTime> verificationExpiryCache = new ConcurrentHashMap<>();

    @Override
    public List<Caregiver> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Caregiver> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Caregiver save(Caregiver caregiver) {
        return repository.save(caregiver);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Caregiver> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Caregiver> searchByServiceArea(String serviceCity, String serviceDistrict) {
        return repository.findByServiceCityContainingAndServiceDistrictContaining(
                serviceCity != null ? serviceCity : "",
                serviceDistrict != null ? serviceDistrict : ""
        );
    }

    @Override
    public void cacheRegistrationData(RegisterRequest request) {
        // ✅ 只暫存註冊資料，不直接存入資料庫
        registrationCache.put(request.getEmail(), request);
    }



    @Override
    public RegisterRequest getCachedRegistrationData(String email) {
        return registrationCache.get(email);
    }

    @Override
    public void removeCachedRegistrationData(String email) {
        // ✅ 只清除驗證碼，不清除註冊資料
        verificationCodeCache.remove(email);
        verificationExpiryCache.remove(email);
    }


    @Override
    public void saveVerificationCode(String email, String verificationCode, LocalDateTime expiresAt) {
        // ✅ 確保驗證碼與過期時間正確保存
        verificationCodeCache.put(email, verificationCode);
        verificationExpiryCache.put(email, expiresAt);
    }


    @Override
    public boolean verifyCode(String email, String inputCode) {
        // ✅ 檢查是否過期
        if (isVerificationCodeExpired(email)) {
            removeCachedRegistrationData(email);  // ✅ 只清除驗證碼，不清除註冊資料
            return false;
        }

        // ✅ 檢查驗證碼是否正確
        String storedCode = verificationCodeCache.get(email);
        boolean isValid = storedCode != null && storedCode.equals(inputCode);

        // ✅ 驗證成功後清除驗證碼
        if (isValid) {
            verificationCodeCache.remove(email);
            verificationExpiryCache.remove(email);
        }

        return isValid;
    }


    @Override
    public boolean isVerificationCodeExpired(String email) {
        LocalDateTime expiryTime = verificationExpiryCache.get(email);
        return expiryTime == null || expiryTime.isBefore(LocalDateTime.now());
    }
}
