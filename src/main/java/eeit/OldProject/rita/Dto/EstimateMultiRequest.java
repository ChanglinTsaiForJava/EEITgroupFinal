package eeit.OldProject.rita.Dto;

import java.util.List;
import java.util.Map;

public class EstimateMultiRequest {
    private Long caregiverId;
    private String startDate;
    private String endDate;
    private List<Map<String, String>> timeSlots;

    // Getter 和 Setter 方法 (請確保都有)

    public Long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Map<String, String>> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<Map<String, String>> timeSlots) {
        this.timeSlots = timeSlots;
    }
}