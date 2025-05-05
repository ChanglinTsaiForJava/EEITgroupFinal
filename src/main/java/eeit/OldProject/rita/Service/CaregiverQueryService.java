package eeit.OldProject.rita.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import eeit.OldProject.rita.Repository.AppointmentTimeContinuousRepository;
import eeit.OldProject.rita.Repository.AppointmentTimeMultiRepository;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CaregiverQueryService {

	private final CaregiversRepository caregiversRepository;
	private final AppointmentTimeContinuousRepository appointmentTimeContinuousRepository;
	private final AppointmentTimeMultiRepository appointmentTimeMultiRepository;

	public List<Caregiver> findAvailableCaregivers(
	        String serviceCity,
	        String serviceDistrict,
	        LocalDateTime desiredStartTime,
	        LocalDateTime desiredEndTime,
	        LocalDate multiStartDate,
	        LocalDate multiEndDate,
	        List<String> repeatDays,
	        String gender,
	        String nationality,
	        String languages,
	        BigDecimal hourlyRateMin,
	        BigDecimal hourlyRateMax) {
	
    List<Caregiver> candidates = (serviceDistrict == null || serviceDistrict.isBlank()) ?
	            caregiversRepository.findByServiceCityContaining(serviceCity) :
	            caregiversRepository.findByServiceCityContainingAndServiceDistrictContaining(serviceCity, serviceDistrict);

	    Set<Long> occupiedIds = new HashSet<>();

	    // Continuous 模式
	    if (desiredStartTime != null && desiredEndTime != null) {
	        List<Long> occupiedInContinuous = appointmentTimeContinuousRepository.findOccupiedCaregiversInContinuous(
	                Date.from(desiredStartTime.atZone(ZoneId.systemDefault()).toInstant()),
	                Date.from(desiredEndTime.atZone(ZoneId.systemDefault()).toInstant()));
	        occupiedIds.addAll(occupiedInContinuous);
	    }

	    // Multi 模式
	    if (multiStartDate != null && multiEndDate != null && repeatDays != null && !repeatDays.isEmpty()) {
	        Map<DayOfWeek, java.util.function.Function<LocalDate, List<Long>>> fetchByDayMap = Map.of(
	                DayOfWeek.MONDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnMonday,
	                DayOfWeek.TUESDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnTuesday,
	                DayOfWeek.WEDNESDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnWednesday,
	                DayOfWeek.THURSDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnThursday,
	                DayOfWeek.FRIDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnFriday,
	                DayOfWeek.SATURDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnSaturday,
	                DayOfWeek.SUNDAY, appointmentTimeMultiRepository::findOccupiedCaregiversOnSunday
	        );

	        multiStartDate.datesUntil(multiEndDate.plusDays(1))
	                .filter(date -> repeatDays.contains(mapDayOfWeekToChinese(date.getDayOfWeek())))
	                .forEach(date -> {
	                    List<Long> occupied = fetchByDayMap.get(date.getDayOfWeek()).apply(date);
	                    occupiedIds.addAll(occupied);
	                });
	    }

	    return candidates.stream()
	            .filter(c -> !occupiedIds.contains(c.getCaregiverId()))
	            .filter(c -> gender == null || c.getGender().equalsIgnoreCase(gender))
	            .filter(c -> nationality == null || c.getNationality().equalsIgnoreCase(nationality))
	            .filter(c -> languages == null || (c.getLanguages() != null && c.getLanguages().contains(languages)))
	            .filter(c -> hourlyRateMin == null || c.getHourlyRate().compareTo(hourlyRateMin) >= 0)
	            .filter(c -> hourlyRateMax == null || c.getHourlyRate().compareTo(hourlyRateMax) <= 0)
	            .toList();
	}


	private String mapDayOfWeekToChinese(DayOfWeek day) {
		return switch (day) {
		case MONDAY -> "星期一";
		case TUESDAY -> "星期二";
		case WEDNESDAY -> "星期三";
		case THURSDAY -> "星期四";
		case FRIDAY -> "星期五";
		case SATURDAY -> "星期六";
		case SUNDAY -> "星期日";
		};
	}

}
