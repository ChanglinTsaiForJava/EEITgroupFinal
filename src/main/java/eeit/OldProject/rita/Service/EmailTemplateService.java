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
     * 生成新預約通知 Email 內容
     */
    public String generateNewAppointmentNotifyContent(Appointment appointment) {
        return String.format(
                """
                <html>
                    <body>
                        <h2>[Care+ 看護服務] 新預約通知</h2>
                        <p>親愛的 %s 您好，</p>
                        <p>您收到一筆新的照護服務預約申請，請登入後台查看詳細需求並回覆是否接受。</p>
                        <ul>
                            <li><strong>預約編號：</strong> %d</li>
                            <li><strong>顧客：</strong> %s</li>
                            <li><strong>金額：</strong> NT$ %s</li>
                        </ul>
                        <p><a href="https://careplus.tw/caregiver/dashboard">👉 查看預約詳情</a></p>
                        <p>若有任何問題，請洽客服：support@careplus.tw</p>
                    </body>
                </html>
                """,
                appointment.getCaregiver().getCaregiverName(),
                appointment.getAppointmentId(),
                appointment.getUser().getUserName(),
                appointment.getTotalPrice().toPlainString()
        );
    }

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

        return String.format(
                """
                <html>
                    <body>
                        <h2>[Care+ 看護服務] 預約付款成功通知</h2>
                        <p>親愛的 %s 您好，</p>
                        <p>感謝您使用 Care+ 看護預約服務，您的付款已成功完成，以下是您的交易明細：</p>
                        <ul>
                            <li><strong>預約編號：</strong> %d</li>
                            <li><strong>看護：</strong> %s</li>
                            <li><strong>服務時間：</strong> %s</li>
                            <li><strong>地點：</strong> %s</li>
                            <li><strong>地址：</strong> %s</li>
                            <li><strong>金額：</strong> NT$ %s</li>
                        </ul>
                        <p>PDF 合約下載： [PDF 合約連結]</p>
                        <p>收據下載： [付款成功收據連結]</p>
                        <p>若有任何問題，歡迎聯絡客服：support@careplus.tw</p>
                    </body>
                </html>
                """,
                appointment.getUser().getUserName(),
                appointment.getAppointmentId(),
                appointment.getCaregiver().getCaregiverName(),
//                totalTime,
                locationType.name(),
                address,
                payment.getFinalAmount().toPlainString()
        );
    }

    /**
     * 生成合約提醒 Email 內容
     */
    public String generateContractReminderContent(Appointment appointment) {
        LocationType locationType = appointment.getLocationType();
        if (locationType == null) locationType = LocationType.醫院;

        String address = locationType == LocationType.醫院 ?
                appointment.getHospitalName() + ", " + appointment.getHospitalAddress() :
                appointment.getHomeAddress();

        return String.format(
                """
                <html>
                    <body>
                        <h2>[Care+ 看護服務] 看護已接受您的預約！</h2>
                        <p>親愛的 %s 您好，</p>
                        <p>您的預約已由看護「%s」接受，請盡快確認合約內容並完成付款。</p>
                        <ul>
                            <li><strong>預約編號：</strong> %d</li>
                            <li><strong>服務地點：</strong> %s - %s</li>
                        </ul>
                        <p><a href="https://careplus.tw/appointment/%d/contract">👉 點我確認合約與付款</a></p>
                        <p>若有任何問題，歡迎聯絡客服：support@careplus.tw</p>
                    </body>
                </html>
                """,
                appointment.getUser().getUserName(),
                appointment.getCaregiver().getCaregiverName(),
                appointment.getAppointmentId(),
                locationType.name(),
                address,
                appointment.getAppointmentId()
        );
    }
}
