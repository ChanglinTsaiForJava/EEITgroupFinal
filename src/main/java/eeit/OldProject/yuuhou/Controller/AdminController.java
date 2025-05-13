package eeit.OldProject.yuuhou.Controller;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Service.CaregiversService;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // 確保整個 controller 只有 ADMIN 能存取
public class AdminController {

    @Autowired
    private CaregiversService caregiversService;

    // ✅ 取得所有照顧者資料
    @GetMapping("/caregivers")
    public ResponseEntity<List<Caregiver>> getAllCaregivers() {
        List<Caregiver> caregivers = caregiversService.findAll();
        return ResponseEntity.ok(caregivers);
    }

    // ✅ 刪除照顧者（封鎖、違規等用途）
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

    // ✅ 🔥【新加的】搜尋照顧者
    @GetMapping("/caregivers/search")
    public ResponseEntity<?> searchCaregivers(
            @RequestParam(required = false) String serviceCity,
            @RequestParam(required = false) String serviceDistrict) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ 你不是超級使用者，無法使用搜尋功能！");
        }

        List<Caregiver> result = caregiversService.searchByServiceArea(serviceCity, serviceDistrict);
        return ResponseEntity.ok(result);
    }

    // ✅ 發送公告（這邊只是展示，實際通知可整合 Email 或推播）
    @PostMapping("/announcement")
    public ResponseEntity<String> broadcastMessage(@RequestBody @NotBlank String message) {
        // TODO: 實作通知機制
        System.out.println("📢 管理員公告：" + message);
        return ResponseEntity.ok("已廣播訊息：「" + message + "」");
    }

    // ✅ 測試管理者權限是否能通過
    @GetMapping("/test")
    public ResponseEntity<String> testAdminAccess() {
        return ResponseEntity.ok("你是超級使用者，可以看到這段訊息 🎉");
    }
    
    @PutMapping("/caregivers/{id}")
    public ResponseEntity<?> updateCaregiver(@PathVariable Long id, @RequestBody Caregiver updatedCaregiver) {
        Optional<Caregiver> existingOpt = caregiversService.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ 找不到這個照顧者！");
        }
        
        Caregiver existing = existingOpt.get();
        
        // ✅ 保留原有密碼，如果前端沒有提供
        if (updatedCaregiver.getPassword() == null || updatedCaregiver.getPassword().isEmpty()) {
            updatedCaregiver.setPassword(existing.getPassword());
        }

        // ✅ 保留原有照片，如果前端沒有提供
        if (updatedCaregiver.getPhoto() == null || updatedCaregiver.getPhoto().length == 0) {
            updatedCaregiver.setPhoto(existing.getPhoto());
        }

        // ✅ 保留 Email，不允許更改
        updatedCaregiver.setEmail(existing.getEmail());
        updatedCaregiver.setCaregiverId(existing.getCaregiverId());
        updatedCaregiver.setCreatedAt(existing.getCreatedAt());
        updatedCaregiver.setVerified(existing.isVerified());

        caregiversService.save(updatedCaregiver);
        return ResponseEntity.ok("✅ 照顧者資料已成功更新！");
    }
}
