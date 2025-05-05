package eeit.OldProject.yuuhou.Controller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Service.CaregiversService;
@RestController
@RequestMapping("/api/caregivers")
public class CaregiversController {
    @Autowired
    private CaregiversService caregiversService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("找不到照顧者");
        }
        Caregiver caregiver = caregiverOpt.get();
        try {
            String folder = "src/main/resources/static/yuuhou/images/";
            String filename = "caregiver_" + caregiver.getCaregiverId() + "_" + file.getOriginalFilename();
            File dest = new File(folder + filename);
            file.transferTo(dest);
            caregiver.setPhotoPath("/yuuhou/images/" + filename);
            caregiversService.save(caregiver);
            return ResponseEntity.ok("✅ 上傳成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ 上傳失敗！");
        }
    }
    // ✅ 搜尋功能（依照登入身分決定資料揭露）
    @GetMapping("/search")
    public ResponseEntity<?> searchCaregivers(@RequestParam(required = false) String serviceCity,
                                              @RequestParam(required = false) String serviceDistrict) {
        List<Caregiver> results = caregiversService.searchByServiceArea(serviceCity, serviceDistrict);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return ResponseEntity.ok(results); // Admin 可看所有欄位
        }
        // 其他人只能看部分欄位（手動映射出來）
        List<?> safeResults = results.stream().map(c -> {
            return new Object() {
                public final Long id = c.getCaregiverId();
                public final String caregiverName = c.getCaregiverName();
                public final String gender = c.getGender();
                public final String nationality = c.getNationality();
                public final int yearOfExperience = c.getYearOfExperience();
                public final String serviceCity = c.getServiceCity();
                public final String serviceDistrict = c.getServiceDistrict();
                public final BigDecimal hourlyRate = c.getHourlyRate();
                public final BigDecimal halfDayRate = c.getHalfDayRate();
                public final BigDecimal fullDayRate = c.getFullDayRate();
                public final String photoPath = c.getPhotoPath();
            };
        }).toList();
        return ResponseEntity.ok(safeResults);
    }
    @GetMapping
    public List<Caregiver> getAll() {
        return caregiversService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Caregiver> caregiverOpt = caregiversService.findById(id);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Caregiver caregiver = caregiverOpt.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return ResponseEntity.ok(caregiver);
        }
        if (!caregiver.getEmail().equals(currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("❌ 你沒有權限查看這個照顧者的資料！");
        }
        return ResponseEntity.ok(caregiver);
    }
    @PostMapping
    public Caregiver create(@RequestBody Caregiver caregiversEntity) {
        return caregiversService.save(caregiversEntity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Caregiver updatedCaregiver) {
        Optional<Caregiver> existingOpt = caregiversService.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Caregiver existing = existingOpt.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !existing.getEmail().equals(currentUserEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ 你不能修改別人的資料！");
        }
        if (!existing.getEmail().equals(updatedCaregiver.getEmail())) {
            Optional<Caregiver> emailCheck = caregiversService.findByEmail(updatedCaregiver.getEmail());
            if (emailCheck.isPresent()) {
                return ResponseEntity.badRequest().body("❌ 這個 Email 已經被使用了！");
            }
        }
        if (updatedCaregiver.getPassword() == null || updatedCaregiver.getPassword().isEmpty()) {
            updatedCaregiver.setPassword(existing.getPassword());
        } else {
            updatedCaregiver.setPassword(passwordEncoder.encode(updatedCaregiver.getPassword()));
        }
        updatedCaregiver.setCaregiverId(id);
        caregiversService.save(updatedCaregiver);
        return ResponseEntity.ok("✅ 修改成功！");
    }
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

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody Caregiver updatedCaregiver) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(currentUserEmail);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ 找不到登入的照顧者帳號！");
        }

        Caregiver existing = caregiverOpt.get();

        // 若 email 有更改，確認沒人使用
        if (!existing.getEmail().equals(updatedCaregiver.getEmail())) {
            Optional<Caregiver> emailCheck = caregiversService.findByEmail(updatedCaregiver.getEmail());
            if (emailCheck.isPresent()) {
                return ResponseEntity.badRequest().body("❌ 這個 Email 已經被使用了！");
            }
        }

        // 處理密碼欄位
        if (updatedCaregiver.getPassword() == null || updatedCaregiver.getPassword().isEmpty()) {
            updatedCaregiver.setPassword(existing.getPassword());
        } else {
            updatedCaregiver.setPassword(passwordEncoder.encode(updatedCaregiver.getPassword()));
        }

        // 保留舊的 ID
        updatedCaregiver.setCaregiverId(existing.getCaregiverId());
        caregiversService.save(updatedCaregiver);

        return ResponseEntity.ok("✅ 你的個人資料已更新！");
    }

    
    
}
