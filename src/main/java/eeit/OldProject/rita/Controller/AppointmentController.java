package eeit.OldProject.rita.Controller;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Service.AppointmentService;
import eeit.OldProject.rita.dto.AppointmentCreateRequest;
import eeit.OldProject.rita.dto.AppointmentFullRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointment") // 所有路徑都以 /api/appointments 開頭
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * ➕ 新增預約（顧客送出需求單時呼叫）
     * POST /api/appointments
     * RequestBody：Appointment JSON 資料
     */
    @PostMapping("/full")
    public ResponseEntity<Appointment> createAppointmentWithDetails(
            @RequestBody AppointmentFullRequest request) {
        Appointment saved = appointmentService.createWithDetails(
                request.getAppointment(),
                request.getDiseases(),
                request.getPhysicals(),
                request.getServices(),
                Optional.ofNullable(request.getContinuous()),
                Optional.ofNullable(request.getMulti())
        );
        return ResponseEntity.ok(saved);
    }

    /**
     * 🔍 查詢單一預約
     * GET /api/appointments/{id}
     * PathVariable：預約 ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        return appointmentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 📋 查詢某個使用者的所有預約
     * GET /api/appointments/user/{userId}
     * PathVariable：使用者 ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>>getUserAppointments(@PathVariable Long userId){
        return ResponseEntity.ok(appointmentService.getByUserId(userId));
    }

    /**
     * ✏️ 更新預約狀態（例如看護接受、顧客付款等）
     * PUT /api/appointments/{id}/status?status=Paid
     * PathVariable：預約 ID
     * RequestParam：狀態 enum 值（Pending, CaregiverConfirmed, Paid, Completed, Cancelled）
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestParam Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }

    /**
     * ❌ 刪除預約（例如顧客取消、管理員清除）
     * DELETE /api/appointments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content 表示成功刪除，不需要回傳任何內容
    }

    /** 顧客同意合約**/
    @PutMapping("/{id}/contract")
    public ResponseEntity<Appointment> confirmContract(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.confirmContract(id));
    }

    /**顧客付款完成（模擬 ECPay callback）**/
    @PutMapping("/{id}/pay")
    public ResponseEntity<Appointment> payAppointment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(appointmentService.markAsPaid(id));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** 看護接受預約**/
    @PutMapping("/{id}/accept")
    public ResponseEntity<Appointment> acceptAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.acceptByCaregiver(id));
    }

}
