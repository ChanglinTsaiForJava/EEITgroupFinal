package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.Patient;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.PatientRepository;
import eeit.OldProject.steve.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    // 新增病患
    @Override
    public Patient createPatient(Patient patient, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("使用者不存在"));
        patient.setUser(user); // 設定病患的 user
        return patientRepository.save(patient); // 儲存病患
    }

    // 更新病患資料
    @Override
    public Patient updatePatient(Long patientId, Patient patient, Long userId) {
        Patient existing = patientRepository.findById(patientId)
                .filter(p -> p.getUser().getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("病患不存在或無權修改"));

        patient.setPatientId(existing.getPatientId());
        patient.setUser(existing.getUser()); // 保持原來的 userId
        return patientRepository.save(patient); // 儲存更新後的病患資料
    }

    // 刪除病患資料
    @Override
    public void deletePatient(Long patientId, Long userId) {
        Patient patient = patientRepository.findById(patientId)
                .filter(p -> p.getUser().getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("病患不存在或無權刪除"));

        patientRepository.delete(patient); // 刪除病患資料
    }

    // 查詢當前使用者的所有病患資料
    @Override
    public List<Patient> getAllPatientsByUserId(Long userId) {
        return patientRepository.findAll().stream()
                .filter(p -> p.getUser().getUserId().equals(userId))
                .toList();
    }

    // 根據病患ID查詢病患資料
    @Override
    public Optional<Patient> getPatientById(Long patientId, Long userId) {
        return patientRepository.findById(patientId)
                .filter(p -> p.getUser().getUserId().equals(userId));
    }
}
