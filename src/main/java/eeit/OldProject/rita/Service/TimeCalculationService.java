package eeit.OldProject.rita.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;

//✅ 改進後的 TimeCalculationService.java
@Service
public class TimeCalculationService {

	@Autowired
	private CaregiversRepository caregiversRepository;

	// 計算連續時間
	public BigDecimal calculateContinuousAmount(Long caregiverId, String startTime, String endTime) {
		Caregiver caregiver = caregiversRepository.findById(caregiverId)
				.orElseThrow(() -> new IllegalArgumentException("找不到看護，ID：" + caregiverId));

		LocalDateTime start = LocalDateTime.parse(startTime);
		LocalDateTime end = LocalDateTime.parse(endTime);

		// 計算總小時數
		long minutes = Duration.between(start, end).toMinutes();
		BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

		// 計算費用
		if (hours.compareTo(BigDecimal.valueOf(4)) <= 0) {
			return hours.multiply(caregiver.getHourlyRate());
		} else if (hours.compareTo(BigDecimal.valueOf(8)) <= 0) {
			return caregiver.getHalfDayRate();
		} else {
			return caregiver.getFullDayRate();
		}
	}

	// 計算多時段時間
	public BigDecimal calculateMultiAmount(Long caregiverId, String startDate, String endDate,
			List<Map<String, String>> timeSlots) {
		Caregiver caregiver = caregiversRepository.findById(caregiverId)
				.orElseThrow(() -> new IllegalArgumentException("找不到看護，ID：" + caregiverId));

		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		BigDecimal totalAmount = BigDecimal.ZERO;

		// ** 加入星期過濾 **
		while (!start.isAfter(end)) {
			for (Map<String, String> slot : timeSlots) {
				String slotStartTime = slot.get("startTime");
				String slotEndTime = slot.get("endTime");

				// 確保時間格式正確
				if (slotStartTime == null || slotEndTime == null) {
					throw new IllegalArgumentException("時間槽缺少開始或結束時間");
				}

				// 計算單日時段費用
				LocalDateTime slotStart = LocalDateTime.parse(start + "T" + slotStartTime);
				LocalDateTime slotEnd = LocalDateTime.parse(start + "T" + slotEndTime);
				long minutes = Duration.between(slotStart, slotEnd).toMinutes();
				BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

				// 計算費用
				if (hours.compareTo(BigDecimal.valueOf(4)) <= 0) {
					totalAmount = totalAmount.add(hours.multiply(caregiver.getHourlyRate()));
				} else if (hours.compareTo(BigDecimal.valueOf(8)) <= 0) {
					totalAmount = totalAmount.add(caregiver.getHalfDayRate());
				} else {
					totalAmount = totalAmount.add(caregiver.getFullDayRate());
				}
			}
			start = start.plusDays(1);
		}

		return totalAmount;
	}
}
