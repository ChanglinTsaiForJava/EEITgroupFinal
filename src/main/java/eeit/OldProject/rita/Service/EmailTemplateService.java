package eeit.OldProject.rita.Service;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Entity.LocationType;
import eeit.OldProject.rita.Entity.Payment;
import eeit.OldProject.rita.Repository.AppointmentTimeContinuousRepository;
import eeit.OldProject.rita.Repository.AppointmentTimeMultiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final TimeCalculationService timeCalculationService;
    private final AppointmentTimeContinuousRepository continuousRepository;
    private final AppointmentTimeMultiRepository multiRepository;

    public String generateNewAppointmentNotifyContent(Appointment appointment) {
        return """
            [Care+ 看護服務] 新預約通知

            親愛的 %s 您好，

            您收到一筆新的照護服務預約申請，請登入後台查看詳細需求並回覆是否接受。

            🔖 預約編號：%d
            👤 顧客：%s
            💰 金額：NT$ %s

            👉 查看預約詳情：
            https://careplus.tw/caregiver/dashboard

            若有任何問題，請洽客服：support@careplus.tw
            """.formatted(
                appointment.getCaregiver().getCaregiverName(),
                appointment.getAppointmentId(),
                appointment.getUser().getUserName(),
                appointment.getTotalPrice().toPlainString()
        );
    }



    public String generateAppointmentPaidContent(Appointment appointment, Payment payment) {
        String totalTime = timeCalculationService.calculateTotalTime(
                continuousRepository.findByAppointmentId(appointment.getAppointmentId()),
                multiRepository.findByAppointmentId(appointment.getAppointmentId())
        );

        /** 確保 locationType 不為 null **/
        LocationType locationType = appointment.getLocationType();
        if (locationType == null) {
            locationType = LocationType.醫院;
        }
        String locationTypeName = locationType.name();  // 醫院 或 居家
        String address = "";


        /** 根據 locationType 顯示具體地址 **/
        if (locationType == LocationType.醫院) {
            // 顯示醫院的名稱和地址
            address = appointment.getHospitalName() + ", " + appointment.getHospitalAddress();
        } else if (locationType == LocationType.居家) {
            // 顯示居家的地址
            address = appointment.getHomeAddress();
        }

        return """
                    [Care+ 看護服務] 預約付款成功通知
                
                    親愛的 %s 您好，
                
                    感謝您使用 Care+ 看護預約服務，您的付款已成功完成，以下是您的交易明細：
                
                    預約編號：%d
                    看護：%s
                    服務時間：%s
                    地點：%s
                    地址：%s
                    金額：NT$ %s
                
                    PDF 合約下載： [PDF 合約連結]
                    收據下載： [付款成功收據連結]
                
                    若有任何問題，歡迎聯絡客服。
                    support@careplus.tw
                """.formatted(
                appointment.getUser().getUserName(),
                appointment.getAppointmentId(),
                appointment.getCaregiver().getCaregiverName(),
                totalTime,
                locationTypeName,
                address,
                payment.getFinalAmount().toPlainString()
        );


    }

    public String generateContractReminderContent(Appointment appointment) {
        LocationType locationType = appointment.getLocationType();
        if (locationType == null) locationType = LocationType.醫院;

        String address = locationType == LocationType.醫院
                ? appointment.getHospitalName() + ", " + appointment.getHospitalAddress()
                : appointment.getHomeAddress();

        return """
                [Care+ 看護服務] 看護已接受您的預約！
                
                親愛的 %s 您好，
                
                您的預約已由看護「%s」接受，請盡快確認合約內容並完成付款。
                
                預約編號：%d
                服務地點：%s - %s
                
                👉 請至合約頁面確認細節與付款：
                https://careplus.tw/appointment/%d/contract
                
                若有任何問題，歡迎聯絡客服：support@careplus.tw
                """.formatted(
                appointment.getUser().getUserName(),
                appointment.getCaregiver().getCaregiverName(),
                appointment.getAppointmentId(),
                locationType.name(),
                address,
                appointment.getAppointmentId()
        );
    }
}

