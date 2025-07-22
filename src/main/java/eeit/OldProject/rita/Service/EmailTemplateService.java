package eeit.OldProject.rita.Service;

import org.springframework.stereotype.Service;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Entity.LocationType;
import eeit.OldProject.rita.Entity.Payment;
import eeit.OldProject.rita.Repository.AppointmentTimeContinuousRepository;
import eeit.OldProject.rita.Repository.AppointmentTimeMultiRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final TimeCalculationService timeCalculationService;
    private final AppointmentTimeContinuousRepository continuousRepository;
    private final AppointmentTimeMultiRepository multiRepository;

    /**
     * 生成預約付款成功通知 Email 內容
     */
    public String generateAppointmentPaidContent(Appointment appointment, Payment payment) {
//        String totalTime = timeCalculationService.calculateTotalTime(
//                continuousRepository.findByAppointmentId(appointment.getAppointmentId()),
//                multiRepository.findByAppointmentId(appointment.getAppointmentId())
//        );

        LocationType locationType = appointment.getLocationType();
        if (locationType == null) {
            locationType = LocationType.醫院;
        }

        String address = locationType == LocationType.醫院 ?
                appointment.getHospitalName() + ", " + appointment.getHospitalAddress() :
                appointment.getHomeAddress();

        String customAppointmentId = "carePlus05222id" + appointment.getAppointmentId();

        return String.format(
                """
                <html>
                    <body>
                        <h2>[Care+ 看護服務] 預約付款成功通知</h2>
                        <p>親愛的 %s 您好，</p>
                        <p>感謝您使用 Care+ 看護預約服務，您的預約已建立成功，以下是您的預約資訊：</p>
                        <ul>
                            <li><strong>預約編號：</strong> %s</li>
                            <li><strong>看護：</strong> %s</li>
                            <li><strong>地點：</strong> %s</li>
                            <li><strong>地址：</strong> %s</li>
                            <li><strong>金額：</strong> NT$ %s</li>
                        </ul>
                        <p>若有任何問題，歡迎聯絡客服：support@careplus.tw</p>
                    </body>
                </html>
                """,
                appointment.getUser().getUserName(),
                customAppointmentId,
                appointment.getCaregiver().getCaregiverName(),
                locationType.name(),
                address,
                appointment.getTotalPrice().toPlainString()
        );
    }
}
