package eeit.OldProject.yuuhou.Controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<String> deleteCaregiver(@PathVariable Long id) {
    	caregiversService.deleteById(id);
        return ResponseEntity.ok("照顧者已成功刪除 ID: " + id);
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
}
