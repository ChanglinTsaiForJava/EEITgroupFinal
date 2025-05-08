package eeit.OldProject.rita.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.rita.Dto.AppointmentFullRequest;
import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Service.AppointmentQueryService;
import eeit.OldProject.rita.Service.AppointmentService;
import eeit.OldProject.rita.Service.AppointmentWorkflowService;
import eeit.OldProject.rita.Service.CaregiverQueryService;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointment") // 所有路徑都以 /api/appointment 開頭
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentQueryService appointmentQueryService;
    private final AppointmentWorkflowService appointmentWorkflowService;
    private final CaregiverQueryService caregiverQueryService;

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
        return appointmentQueryService.getById(id)
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
        return ResponseEntity.ok(appointmentQueryService.getByUserId(userId));
    }

    /**
     * ✏️ 單純更新預約狀態（例如看護接受、顧客付款等）
     * PUT /api/appointments/{id}/status?status=Paid
     * PathVariable：預約 ID
     * RequestParam：狀態 enum 值（Pending, CaregiverConfirmed, Paid, Completed, Cancelled）
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestParam Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(appointmentWorkflowService.updateStatus(id, status));
    }

    /**
     * ❌ 刪除預約（例如顧客取消、管理員清除）
     * DELETE /api/appointments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentQueryService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content 表示成功刪除，不需要回傳任何內容
    }

    /** 看護接受預約**/
    @PutMapping("/{id}/accept")
    public ResponseEntity<Appointment> acceptAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentWorkflowService.acceptAndCreatePayment(id));
    }

    /** 顧客同意合約**/
    @PutMapping("/{id}/contract")
    public ResponseEntity<Appointment> confirmContract(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentWorkflowService.confirmContract(id));
    }

    /**顧客付款完成（模擬 ECPay callback）**/
    @PutMapping("/{id}/pay")
    public ResponseEntity<Appointment> payAppointment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(appointmentWorkflowService.markAsPaid(id));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** 透過條件查詢**/
    @GetMapping("/caregiver/available")
    public ResponseEntity<List<Caregiver>> searchAvailableCaregivers(
            @RequestParam String serviceCity,
            @RequestParam(required = false) String serviceDistrict,

            // Continuous 模式用
            @RequestParam(required = false) String desiredStartTime,
            @RequestParam(required = false) String desiredEndTime,

            // 移除 Multi 模式的 repeatDays
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,

            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String languages,
            @RequestParam(required = false) BigDecimal hourlyRateMin,
            @RequestParam(required = false) BigDecimal hourlyRateMax
    ) {

        // 打印出接收到的時間參數
        System.out.println("Start time: " + desiredStartTime);
        System.out.println("End time: " + desiredEndTime);

        List<Caregiver> caregivers;

        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // 檢查 desiredStartTime 和 desiredEndTime 是否為空
        if (desiredStartTime != null && !desiredStartTime.isEmpty() && desiredEndTime != null && !desiredEndTime.isEmpty()) {
            try {
                LocalDateTime start = LocalDateTime.parse(desiredStartTime, dtFormatter);
                LocalDateTime end = LocalDateTime.parse(desiredEndTime, dtFormatter);

                caregivers = caregiverQueryService.findAvailableCaregivers(
                        serviceCity, serviceDistrict, start, end,
                        null, null, null,
                        gender, nationality, languages, hourlyRateMin, hourlyRateMax
                );
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing datetime: " + e.getMessage());
                return ResponseEntity.badRequest().body(null); // 返回錯誤，表示時間解析錯誤
            }
        } else if (startDate != null && endDate != null) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);

                caregivers = caregiverQueryService.findAvailableCaregivers(
                        serviceCity, serviceDistrict, null, null,
                        start, end, null,  // 移除 repeatDays
                        gender, nationality, languages, hourlyRateMin, hourlyRateMax
                );
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
                return ResponseEntity.badRequest().body(null); // 返回錯誤，表示日期解析錯誤
            }
        } else {
            return ResponseEntity.badRequest().build(); // 如果時間都沒有填寫，返回錯誤
        }
        // 處理圖片路徑
//        caregivers.forEach(caregiver -> {
//            if (caregiver.getPhotoPath() != null && !caregiver.getPhotoPath().isEmpty()) {
//                // 假設圖片存儲在伺服器的 images/caregivers 資料夾中
//                caregiver.setPhotoPath("http://your-server-url/images/caregivers/" + caregiver.getPhotoPath());
//            } else {
//                // 如果沒有圖片，使用預設圖片
//                caregiver.setPhotoPath("http://your-server-url/images/caregivers/default-placeholder.jpg");
//            }
//        });
        return ResponseEntity.ok(caregivers);
    }



}
