package eeit.OldProject.rita.Service;

import eeit.OldProject.rita.Repository.AppointmentTimeContinuousRepository;
import eeit.OldProject.rita.Repository.AppointmentTimeMultiRepository;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            String gender,
            String nationality,
            String languages,
            BigDecimal hourlyRateMin,
            BigDecimal hourlyRateMax
    ) {
        // 🌟 加上 debug 印出篩選條件
        System.out.println("【Caregiver 篩選條件】");
        System.out.println("serviceCity = " + serviceCity);
        System.out.println("serviceDistrict = " + serviceDistrict);
        System.out.println("desiredStartTime = " + desiredStartTime);
        System.out.println("desiredEndTime = " + desiredEndTime);
        System.out.println("gender = " + gender);
        System.out.println("nationality = " + nationality);
        System.out.println("languages = " + languages);
        System.out.println("hourlyRateMin = " + hourlyRateMin);
        System.out.println("hourlyRateMax = " + hourlyRateMax);


        List<Caregiver> candidates = caregiversRepository.findByServiceCityContainingAndServiceDistrictContaining(serviceCity,serviceDistrict);

        // 查被預約走的人
        List<Long> occupiedInContinuous = appointmentTimeContinuousRepository.findOccupiedCaregiversInContinuous(
                Date.from(desiredStartTime.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(desiredEndTime.atZone(ZoneId.systemDefault()).toInstant())
        );

        List<Long> occupiedInMulti = appointmentTimeMultiRepository.findOccupiedCaregiversInMulti(
                desiredStartTime.toLocalDate(),
                desiredEndTime.toLocalDate()
        );

        Set<Long> occupiedIds = new HashSet<>();
        occupiedIds.addAll(occupiedInContinuous);
        occupiedIds.addAll(occupiedInMulti);

        return candidates.stream()
                .filter(c -> !occupiedIds.contains(c.getCaregiverId()))
                .filter(c -> gender == null || c.getGender().equalsIgnoreCase(gender))
                .filter(c -> nationality == null || c.getNationality().equalsIgnoreCase(nationality))
                .filter(c -> languages == null || (c.getLanguages() != null && c.getLanguages().contains(languages)))
                .filter(c -> hourlyRateMin == null || c.getHourlyRate().compareTo(hourlyRateMin) >= 0) // 時薪大於等於 min
                .filter(c -> hourlyRateMax == null || c.getHourlyRate().compareTo(hourlyRateMax) <= 0) // 時薪小於等於 max
                .toList();
    }

}
