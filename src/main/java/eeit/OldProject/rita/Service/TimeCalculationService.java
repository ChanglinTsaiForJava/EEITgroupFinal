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

/**
 * TimeCalculationService：計算照護費用
 * 根據「看護的計價方式」與「使用者選擇的時間範圍」來計算總金額
 */
@Service
public class TimeCalculationService {

	@Autowired
	private CaregiversRepository caregiversRepository;

	// 計算連續時間
	public BigDecimal calculateContinuousAmount(Long caregiverId, String startTime, String endTime) {

		// 1️⃣ 查詢看護資料，取得時薪／半天／全天費率
		Caregiver caregiver = caregiversRepository.findById(caregiverId)
				.orElseThrow(() -> new IllegalArgumentException("找不到看護，ID：" + caregiverId));

		// 2️⃣ 將字串轉換為時間格式
		LocalDateTime start = LocalDateTime.parse(startTime);
		LocalDateTime end = LocalDateTime.parse(endTime);

		// 3️⃣ 計算總分鐘數，並轉換為小時（四捨五入保留兩位小數）
		long minutes = Duration.between(start, end).toMinutes();
		BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

		// 4️⃣ 根據小時計算費用
		if (hours.compareTo(BigDecimal.valueOf(4)) <= 0) { // 若小於等於 4 小時 → 依照時薪計算
			return hours.multiply(caregiver.getHourlyRate());
		} else if (hours.compareTo(BigDecimal.valueOf(8)) <= 0) { // 若介於 4～8 小時 → 以「半日」計費
			return caregiver.getHalfDayRate();
		} else {
			return caregiver.getFullDayRate(); // 若超過 8 小時 → 以「全日」計費
		}
	}

//	// 計算多時段時間
//	public BigDecimal calculateMultiAmount(Long caregiverId, String startDate, String endDate,
//			List<Map<String, String>> timeSlots) {
//		Caregiver caregiver = caregiversRepository.findById(caregiverId)
//				.orElseThrow(() -> new IllegalArgumentException("找不到看護，ID：" + caregiverId));
//
//		LocalDate start = LocalDate.parse(startDate);
//		LocalDate end = LocalDate.parse(endDate);
//		BigDecimal totalAmount = BigDecimal.ZERO;
//
//		// ** 加入星期過濾 **
//		while (!start.isAfter(end)) {
//			for (Map<String, String> slot : timeSlots) {
//				String slotStartTime = slot.get("startTime");
//				String slotEndTime = slot.get("endTime");
//
//				// 確保時間格式正確
//				if (slotStartTime == null || slotEndTime == null) {
//					throw new IllegalArgumentException("時間槽缺少開始或結束時間");
//				}
//
//				// 計算單日時段費用
//				LocalDateTime slotStart = LocalDateTime.parse(start + "T" + slotStartTime);
//				LocalDateTime slotEnd = LocalDateTime.parse(start + "T" + slotEndTime);
//				long minutes = Duration.between(slotStart, slotEnd).toMinutes();
//				BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
//
//				// 計算費用
//				if (hours.compareTo(BigDecimal.valueOf(4)) <= 0) {
//					totalAmount = totalAmount.add(hours.multiply(caregiver.getHourlyRate()));
//				} else if (hours.compareTo(BigDecimal.valueOf(8)) <= 0) {
//					totalAmount = totalAmount.add(caregiver.getHalfDayRate());
//				} else {
//					totalAmount = totalAmount.add(caregiver.getFullDayRate());
//				}
//			}
//			start = start.plusDays(1);
//		}
//
//		return totalAmount;
//	}
}
