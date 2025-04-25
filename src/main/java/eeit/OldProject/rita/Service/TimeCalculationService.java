package eeit.OldProject.rita.Service;

import eeit.OldProject.rita.Entity.AppointmentTimeContinuous;
import eeit.OldProject.rita.Entity.AppointmentTimeMulti;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TimeCalculationService {

    // 計算 AppointmentTimeContinuous 的時數（以小時為單位）
    public double calculateDurationInHours(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            long durationInMillis = endTime.getTime() - startTime.getTime();
            return durationInMillis / (1000.0 * 60.0 * 60.0);  // 返回以小時為單位的時數
        }
        return 0;
    }

    // 用於計算 AppointmentTimeMulti 的時數（以小時為單位）
    public double calculateMultiTimeDurationInHours(AppointmentTimeMulti time) {
        if (time.getStartDate() != null && time.getEndDate() != null && time.getDailyStartTime() != null && time.getDailyEndTime() != null) {
            // 計算每一天的時數
            long startTimeInMillis = time.getDailyStartTime().toNanoOfDay();
            long endTimeInMillis = time.getDailyEndTime().toNanoOfDay();
            double dailyDuration = (endTimeInMillis - startTimeInMillis) / (1000.0 * 60.0 * 60.0);  // 每天的時數

            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(time.getStartDate(), time.getEndDate());
            return daysBetween * dailyDuration;
        }
        return 0;
    }

    // 計算總時數並以 "天 小時" 格式返回
    public String calculateTotalTime(List<AppointmentTimeContinuous> continuousTimes, List<AppointmentTimeMulti> multiTimes) {
        double totalDurationInHours = 0;

        // 計算 AppointmentTimeContinuous 的時數
        if (continuousTimes != null) {
            totalDurationInHours += continuousTimes.stream()
                    .mapToDouble(time -> calculateDurationInHours(time.getStartTime(), time.getEndTime()))
                    .sum();
        }

        // 計算 AppointmentTimeMulti 的時數
        if (multiTimes != null) {
            totalDurationInHours += multiTimes.stream()
                    .mapToDouble(this::calculateMultiTimeDurationInHours)
                    .sum();
        }

        // 計算總天數和小時數
        int days = (int) totalDurationInHours / 24;  // 1天 = 24小時
        int hours = (int) totalDurationInHours % 24; // 剩餘的時間是小時數

        return days + "天 " + hours + "小時";  // 格式化輸出
    }
}

