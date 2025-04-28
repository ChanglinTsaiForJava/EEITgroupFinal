package eeit.OldProject.rita.Dto;

import eeit.OldProject.rita.Entity.*;
import eeit.OldProject.rita.Service.AppointmentService;
import lombok.Data;

import java.util.List;

@Data
public class AppointmentFullRequest {
    private Appointment appointment; // 主預約資料
    private List<AppointmentDisease> diseases; // 疾病狀況
    private List<AppointmentPhysical> physicals; // 身體狀況
    private List<AppointmentServiceItem> services; // 所需服務
    private AppointmentTimeContinuous continuous; // 若為連續時段
    private AppointmentTimeMulti multi; // 若為多時段
}
