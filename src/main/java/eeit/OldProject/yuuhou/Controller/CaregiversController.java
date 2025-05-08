package eeit.OldProject.yuuhou.Controller;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import eeit.OldProject.yuuhou.Service.CaregiversService;
@RestController
@RequestMapping("/api/caregivers")
public class CaregiversController {
    @Autowired
    private CaregiversService caregiversService;
    
    
    
    @Autowired
    private CaregiversRepository caregiversRepository ;

    // ✅ 提供頭貼 API
 
    
    
    
    
    
    
    
    
 // ✅ 確保圖片正確儲存到資料庫
    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestPart("file") MultipartFile file, Authentication authentication) {
        String email = authentication.getName();
        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);

        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("找不到使用者");
        }

        Caregiver caregiver = caregiverOpt.get();

        try {
            byte[] photoBytes = file.getBytes();
            caregiver.setPhoto(photoBytes);
            caregiversService.save(caregiver);

            // ✅ **修改**: 回傳正確的 JSON 格式
            String base64Photo = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(photoBytes);

            // ✅ **確保回傳正確的 Content-Type**
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Map.of("photo", base64Photo));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ 圖片儲存失敗");
        }
    }


    @GetMapping("/photo")
    public ResponseEntity<?> getPhoto(Authentication authentication) {
        String email = authentication.getName();
        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(email);

        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("找不到使用者");
        }

        Caregiver caregiver = caregiverOpt.get();
        byte[] photoData = caregiver.getPhoto();
        if (photoData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有圖片資料");
        }

        String base64 = java.util.Base64.getEncoder().encodeToString(photoData);
        return ResponseEntity.ok(base64);
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

        List<Map<String, Object>> safeResults = results.stream().map(c -> {
            String base64Photo = null;
            if (c.getPhoto() != null) {
                base64Photo = "data:image/jpeg;base64," +
                    java.util.Base64.getEncoder().encodeToString(c.getPhoto());
            }

            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", c.getCaregiverId());
            map.put("caregiverName", c.getCaregiverName());
            map.put("gender", c.getGender());
            map.put("nationality", c.getNationality());
            map.put("yearOfExperience", c.getYearOfExperience());
            map.put("serviceCity", c.getServiceCity());
            map.put("serviceDistrict", c.getServiceDistrict());
            map.put("hourlyRate", c.getHourlyRate());
            map.put("halfDayRate", c.getHalfDayRate());
            map.put("fullDayRate", c.getFullDayRate());
            map.put("photoBase64", base64Photo); // ✅ 正確轉成 base64 字串

            return map;
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
            updatedCaregiver.setPassword(updatedCaregiver.getPassword()); // ✅ 直接使用明碼
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

        // ✅ 保留 Email，不允許更改
        updatedCaregiver.setEmail(existing.getEmail());

        // ✅ 保留原密碼，如果前端沒有提供
        if (updatedCaregiver.getPassword() == null || updatedCaregiver.getPassword().isEmpty()) {
            updatedCaregiver.setPassword(existing.getPassword());
        }

        // ✅ 保留原有照片
        if (updatedCaregiver.getPhoto() == null || updatedCaregiver.getPhoto().length == 0) {
            updatedCaregiver.setPhoto(existing.getPhoto());
        }

        // ✅ 保留其他不能改變的欄位
        updatedCaregiver.setCaregiverId(existing.getCaregiverId());
        updatedCaregiver.setCreatedAt(existing.getCreatedAt());
        updatedCaregiver.setVerified(existing.isVerified());

        // ✅ 更新資料
        caregiversService.save(updatedCaregiver);

        return ResponseEntity.ok("✅ 你的個人資料已更新！");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        Optional<Caregiver> caregiverOpt = caregiversService.findByEmail(currentUserEmail);
        if (caregiverOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ 找不到登入的照顧者帳號！");
        }

        Caregiver caregiver = caregiverOpt.get();
        
        // ✅ 將 byte[] 轉成 base64
        String base64Photo = null;
        if (caregiver.getPhoto() != null && caregiver.getPhoto().length > 0) {
            base64Photo = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(caregiver.getPhoto());
        }

        // ✅ 回傳包含照片的 JSON
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("caregiverId", caregiver.getCaregiverId());
        response.put("caregiverName", caregiver.getCaregiverName());
        response.put("gender", caregiver.getGender());
        response.put("birthday", caregiver.getBirthday());
        response.put("phone", caregiver.getPhone());
        response.put("nationality", caregiver.getNationality());
        response.put("yearOfExperience", caregiver.getYearOfExperience());
        response.put("description", caregiver.getDescription());
        response.put("photo", base64Photo); // ✅ 加入照片

        return ResponseEntity.ok(response);
    }

    
    
    
    
    
}
