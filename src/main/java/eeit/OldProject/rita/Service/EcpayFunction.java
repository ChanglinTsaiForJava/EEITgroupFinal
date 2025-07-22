package eeit.OldProject.rita.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Repository.AppointmentRepository;
import eeit.OldProject.rita.Service.AppointmentWorkflowService;

/**
 * EcpayFunction 整合綠界金流負責：
 * ✅ 根據預約資料建立付款表單（HTML Form）
 * ✅ 產生符合綠界 API 規範的參數（含加密驗證）
 * ✅ 自動導向綠界測試金流頁面進行付款
 *
 * 設計流程如下：
 *
 * Controller 呼叫 → buildEcpayForm(appointmentId)
 *     ├── 查詢預約資料
 *     ├── 建立付款參數 createEcpayData()
 *     │       └── 產生加密欄位 CheckMacValue → genCheckMacValue()
 *     │               └── urlEncode() + hash() + bytesToHex()
 *     └── 生成 HTML form，自動 submit 到綠界金流頁面
 *
 * 測試信用卡帳號：4311 9511 1111 1111
 * 其餘隨意填寫，日期大於當日即可。
 */

@Component
public class EcpayFunction {
    // 綠界金流的測試環境參數
    private static final String ACTION_URL = "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";
    private static final String RETURN_URL = "https://c0e8bd04bf7e.ngrok-free.app/payment/return";
    private static final String RESULT_URL = "https://c0e8bd04bf7e.ngrok-free.app/payment/result";
    private static final String MERCHANT_ID = "2000132";
    private static final String HASH_KEY = "5294y06JbISpM5x9";
    private static final String HASH_IV = "v77hoKGq4kWxNNIS";

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentWorkflowService appointmentWorkflowService;

    /**
     * 根據 appointmentId 產生綠界付款表單
     * @param appointmentId 預約 ID
     * @return 自動送出的 HTML 表單
     */
    public String buildEcpayForm(Long appointmentId) throws Exception {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            // 組成交易參數
            String id = "carePlus05222id" + appointment.getAppointmentId();
            System.out.println("MerchantTradeNo: " + id);
            String name = "Caregiver Service";
            String total = appointment.getTotalPrice().setScale(0, RoundingMode.DOWN).toString();
            String desc = "Care+ Appointment EcPayment";
            String date = "2025/05/13 06:05:23";

            // 生成參數 Map（含 CheckMacValue）
            Map<String, String> parameters = this.createEcpayData(id, name, total, desc, date);

            // 產出表單 HTML，並自動 submit
            StringBuilder builder = new StringBuilder();
//            builder.append("<form id='payForm' action='" + ACTION_URL + "' method='POST'>");
          builder = builder.append("<form id='payForm' target='_blank' action='"+ACTION_URL+"' method='POST'>");

            
            
            for (String key : parameters.keySet()) {
                builder.append("<input type='hidden' name='").append(key).append("' value='").append(parameters.get(key)).append("'/>");
            }
            builder.append("<script>document.getElementById('payForm').submit();</script>");
            builder.append("</form>");
            return builder.toString();
        } else {
            throw new Exception("Appointment not found.");
        }
    }


    /**
     * 建立 ECPay 所需的付款參數欄位，並加入加密驗證欄位 CheckMacValue
     */
    private Map<String, String> createEcpayData(String id, String name, String total, String desc, String date) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("MerchantID", MERCHANT_ID);
        parameters.put("MerchantTradeNo", id);
        parameters.put("ItemName", name);
        parameters.put("TotalAmount", total);
        parameters.put("TradeDesc", desc);
        parameters.put("MerchantTradeDate", date);
        parameters.put("PaymentType", "aio");
        parameters.put("ChoosePayment", "ALL");
        parameters.put("ReturnURL", RETURN_URL);
//        parameters.put("OrderResultURL", RESULT_URL);
//        parameters.put("NeedExtraPaidInfo", "N");

        // ✅ 產生加密驗證碼欄位
        String checkMacValue = genCheckMacValue(parameters, HASH_KEY, HASH_IV);
        parameters.put("CheckMacValue", checkMacValue);
        return parameters;
    }

    /**
     * 依照 ECPay 規定的順序與格式產生 CheckMacValue（安全雜湊）
     */
    private String genCheckMacValue(Map<String, String> params, String hashKey, String hashIV){
        TreeSet<String> treeSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        treeSet.addAll(params.keySet());
        StringBuilder paramStr = new StringBuilder();
        for (String key : treeSet) {
            if (!"CheckMacValue".equals(key)) {
                paramStr.append("&").append(key).append("=").append(params.get(key));
            }
        }
        String urlEncode = urlEncode("Hashkey=" + hashKey + paramStr.toString() + "&HashIV=" + hashIV).toLowerCase();
        urlEncode = urlEncode.replaceAll("%21", "!").replaceAll("%28", "(").replaceAll("%29", ")");
        return hash(urlEncode.getBytes(), "SHA-256");
    }

    /**
     * 將參數進行 URL 編碼（UTF-8）
     */
    private String urlEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 產生 SHA-256 雜湊值（bytes → hex 字串）
     */
    private String hash(byte[] data, String mode){
        try{
            MessageDigest md = MessageDigest.getInstance(mode);
            return bytesToHex(md.digest(data));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 將 byte 陣列轉為 16 進位字串
     */
    private String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
