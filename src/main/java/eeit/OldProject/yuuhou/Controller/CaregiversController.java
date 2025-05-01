package eeit.OldProject.yuuhou.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Service.CaregiversService;

@RestController
@RequestMapping("/api/caregivers")
public class CaregiversController {

    @Autowired
    private CaregiversService caregiversService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ 取得所有照顧者（目前預設開放，之後可以考慮限制）
    @GetMapping
    public List<Caregiver> getAll() {
        return caregiversService.findAll();
    }

    // ✅ 以 ID 取得單一照顧者（加上權限判斷）
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Caregiver> caregiverOpt = caregiversService.findById(id);

        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Caregiver caregiver = caregiverOpt.get();

        // 取得目前登入者資訊
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        // 檢查是否是 Admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(caregiver); // Admin 可以看任何人
        }

        // 如果是一般照顧者，只能看自己
        if (!caregiver.getEmail().equals(currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("❌ 你沒有權限查看這個照顧者的資料！");
        }

        return ResponseEntity.ok(caregiver);
    }

    // ✅ 新增照顧者
    @PostMapping
    public Caregiver create(@RequestBody Caregiver caregiversEntity) {
        return caregiversService.save(caregiversEntity);
    }

    // ✅ 更新照顧者資料
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Caregiver updatedCaregiver) {
        Optional<Caregiver> existingOpt = caregiversService.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Caregiver existing = existingOpt.get();

        // 取得目前登入者資訊
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // ✅ 一般照顧者只能改自己的
        if (!isAdmin && !existing.getEmail().equals(currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ 你不能修改別人的資料！");
        }

        // ✅ 防止 email 重複錯誤
        if (!existing.getEmail().equals(updatedCaregiver.getEmail())) {
            Optional<Caregiver> emailCheck = caregiversService.findByEmail(updatedCaregiver.getEmail());
            if (emailCheck.isPresent()) {
                return ResponseEntity.badRequest().body("❌ 這個 Email 已經被使用了！");
            }
        }

        // ✅ 密碼處理：如果前端送過來的 password 是空的，保留原本；如果有新密碼，重新 encode
        if (updatedCaregiver.getPassword() == null || updatedCaregiver.getPassword().isEmpty()) {
            updatedCaregiver.setPassword(existing.getPassword());
        } else {
            updatedCaregiver.setPassword(passwordEncoder.encode(updatedCaregiver.getPassword()));
        }

        // ✅ 保留原本的 ID
        updatedCaregiver.setCaregiverId(id);

        // 儲存更新
        caregiversService.save(updatedCaregiver);

        return ResponseEntity.ok("✅ 修改成功！");
    }


    // ✅ 刪除照顧者
    @DeleteMapping("/caregivers/{id}")
    public ResponseEntity<?> deleteCaregiver(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ 你不是超級使用者，無法刪除照顧者！");
        }

        caregiversService.deleteById(id);
        return ResponseEntity.ok("✅ 照顧者已成功刪除，ID: " + id);
    }


}
