package eeit.OldProject.rita.Controller;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

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
//import eeit.OldProject.rita.Dto.EstimateMultiRequest;
import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Service.AppointmentQueryService;
import eeit.OldProject.rita.Service.AppointmentService;
import eeit.OldProject.rita.Service.AppointmentWorkflowService;
import eeit.OldProject.rita.Service.CaregiverQueryService;
import eeit.OldProject.rita.Service.TimeCalculationService;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;
	private final AppointmentQueryService appointmentQueryService;
	private final AppointmentWorkflowService appointmentWorkflowService;
	private final CaregiverQueryService caregiverQueryService;
	private final TimeCalculationService timeCalculationService;

	/**
	 * 1.新增預約 Appointment JSON 資料
	 */
	@PostMapping("/full")
	public ResponseEntity<Appointment> createAppointmentWithDetails(@RequestBody AppointmentFullRequest request) {
		Appointment saved = appointmentService.createWithDetails(request.getAppointment(), request.getDiseases(),
				request.getPhysicals(), request.getServices(), Optional.ofNullable(request.getContinuous()),
				Optional.ofNullable(request.getMulti()));
		return ResponseEntity.ok(saved);
	}

	/**
	 * 2.查詢某個使用者的所有預約 PathVariable：使用者 ID
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long userId) {
		return ResponseEntity.ok(appointmentQueryService.getByUserId(userId));
	}

	/** 3. 顧客同意合約 **/
	@PutMapping("/{id}/contract")
	public ResponseEntity<Appointment> confirmContract(@PathVariable Long id) {
		return ResponseEntity.ok(appointmentWorkflowService.confirmContract(id));
	}

	/** 4. 透過篩選條件查詢 **/
	@GetMapping("/caregiver/available")
	public ResponseEntity<List<Caregiver>> searchAvailableCaregivers(
	        @RequestParam String serviceCity,
	        @RequestParam(required = false) String serviceDistrict,
	        @RequestParam(required = false) String desiredStartTime,
	        @RequestParam(required = false) String desiredEndTime,
	        @RequestParam(required = false) String startDate,
	        @RequestParam(required = false) String endDate,
	        @RequestParam(required = false) String gender,
	        @RequestParam(required = false) String nationality,
	        @RequestParam(required = false) String languages,
	        @RequestParam(required = false) BigDecimal hourlyRateMin,
	        @RequestParam(required = false) BigDecimal hourlyRateMax) {

	    List<Caregiver> caregivers;

	    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	    // 檢查連續時間
	    if (desiredStartTime != null && !desiredStartTime.isEmpty() && desiredEndTime != null && !desiredEndTime.isEmpty()) {
	        try {
	            LocalDateTime start = LocalDateTime.parse(desiredStartTime, dtFormatter);
	            LocalDateTime end = LocalDateTime.parse(desiredEndTime, dtFormatter);

	            caregivers = caregiverQueryService.findAvailableCaregivers(serviceCity, serviceDistrict, start, end,
	                    null, null, null, gender, nationality, languages, hourlyRateMin, hourlyRateMax);
	        } catch (DateTimeParseException e) {
	            System.err.println("Error parsing datetime: " + e.getMessage());
	            return ResponseEntity.badRequest().body(null);
	        }
	    }
	    // 檢查日期範圍
	    else if (startDate != null && endDate != null) {
	        try {
	            LocalDate start = LocalDate.parse(startDate);
	            LocalDate end = LocalDate.parse(endDate);

	            caregivers = caregiverQueryService.findAvailableCaregivers(serviceCity, serviceDistrict, null, null,
	                    start, end, null, gender, nationality, languages, hourlyRateMin, hourlyRateMax);
	        } catch (DateTimeParseException e) {
	            System.err.println("Error parsing date: " + e.getMessage());
	            return ResponseEntity.badRequest().body(null);
	        }
	    } else {
	        return ResponseEntity.badRequest().build();
	    }

	    // 處理圖片路徑
	    caregivers.forEach(caregiver -> {
	        String photoPath = caregiver.getPhotoPath();
	        byte[] photoBytes = caregiver.getPhoto();

	        // ✅ **優先抓 URL 圖片**
	        if (photoPath != null && !photoPath.isEmpty()) {
	            // 檢查圖片路徑是否已包含 S3 域名
	            if (!photoPath.startsWith("https://finalimagesbucket.s3.amazonaws.com/")) {
	                caregiver.setPhotoPath("https://finalimagesbucket.s3.amazonaws.com/" + photoPath.replaceAll("^/+", ""));
	            }
	        }
	        // ✅ **如果 URL 無效，抓 byte[] 圖片**
	        else if (photoBytes != null && photoBytes.length > 0) {
	            String base64Photo = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(photoBytes);
	            caregiver.setPhotoPath(base64Photo);
	        }
	        // ✅ **都沒有就用預設圖片**
	        else {
	            caregiver.setPhotoPath("https://finalimagesbucket.s3.amazonaws.com/default-placeholder.jpg");
	        }

	        // 計算總價
	        BigDecimal xxx = timeCalculationService.calculateContinuousAmount(caregiver.getCaregiverId(), desiredStartTime, desiredEndTime);
	        caregiver.setTotalPrice(xxx);
	    });

	    return ResponseEntity.ok(caregivers);
	}

	/**
	 * 5. 查詢單一預約 GET /api/appointments/{id} PathVariable：預約 ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
		return appointmentQueryService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * 6. 將預約狀態標記為 Paid（給前端）
	 */
	@PutMapping("/{id}/mark-paid")
	public ResponseEntity<Appointment> markAppointmentAsPaid(@PathVariable Long id) {
		try {
			Appointment updated = appointmentWorkflowService.markAsPaid(id);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * 7. 手動更新預約狀態使用
	 */
	@PutMapping("/{id}/status")
	public ResponseEntity<Appointment> updateStatus(@PathVariable Long id,
													@RequestParam Appointment.AppointmentStatus status) {
		return ResponseEntity.ok(appointmentWorkflowService.updateStatus(id, status));
	}

//以下為供未來擴充功能使用：
//
//	/** 連續時間 **/
//	@GetMapping("/estimate/continuous")
//	public BigDecimal estimateContinuousAmount(
//	        @RequestParam Long caregiverId,
//	        @RequestParam String startTime,
//	        @RequestParam String endTime) {
//	    return timeCalculationService.calculateContinuousAmount(caregiverId, startTime, endTime);
//	}
//
//	/** 多時段時間 **/
//	@PostMapping("/estimate/multi")
//	public BigDecimal estimateMultiAmount(
//	        @RequestBody EstimateMultiRequest request) {
//	    return timeCalculationService.calculateMultiAmount(
//	            request.getCaregiverId(),
//	            request.getStartDate(),
//	            request.getEndDate(),
//	            request.getTimeSlots()
//	    );
//	}

//
//	/**
//	 * ❌ 刪除預約（例如顧客取消、管理員清除） DELETE /api/appointments/{id}
//	 */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
//		appointmentQueryService.deleteById(id);
//		return ResponseEntity.noContent().build(); // 204 No Content 表示成功刪除，不需要回傳任何內容
//	}
//
//
//	/** 看護接受預約 **/
//	@PutMapping("/{id}/accept")
//	public ResponseEntity<Appointment> acceptAppointment(@PathVariable Long id) {
//		return ResponseEntity.ok(appointmentWorkflowService.acceptAndCreatePayment(id));
//	}
//

}
