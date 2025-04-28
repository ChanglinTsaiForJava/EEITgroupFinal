package eeit.OldProject.rita.Dto;

import eeit.OldProject.rita.Entity.*;
import lombok.Data;

import java.util.List;

@Data
public class AppointmentCreateRequest {
    private Appointment appointment;
    private List<AppointmentDisease> diseases;
    private List<AppointmentPhysical> physicals;
    private List<AppointmentServiceItem> services;
    private AppointmentTimeContinuous continuous;
    private AppointmentTimeMulti multi;
}
