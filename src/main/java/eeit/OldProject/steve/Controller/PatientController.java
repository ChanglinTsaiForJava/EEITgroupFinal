package eeit.OldProject.steve.Controller;

import eeit.OldProject.steve.Entity.Patient;
import eeit.OldProject.steve.Service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // 新增病患
    @PostMapping("/add")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        return ResponseEntity.ok(patientService.createPatient(patient, userId));
    }

    // 更新病患資料
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable("id") Long patientId,
                                           @RequestBody Patient patient,
                                           HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        return ResponseEntity.ok(patientService.updatePatient(patientId, patient, userId));
    }

    // 刪除病患資料
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long patientId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        patientService.deletePatient(patientId, userId);
        return ResponseEntity.ok("刪除成功");
    }

    // 查詢當前使用者的病患資料
    @GetMapping("/my")
    public ResponseEntity<?> getMyPatients(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        List<Patient> patients = patientService.getAllPatientsByUserId(userId);
        return ResponseEntity.ok(patients);
        
    }

    // 查詢特定病患資料
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable("id") Long patientId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("尚未登入");

        return patientService.getPatientById(patientId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
