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

@Component
public class EcpayFunction {
    private static final String ACTION_URL = "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";
    private static final String RETURN_URL = "https://fdf5-1-160-3-119.ngrok-free.app/payment/return";
    private static final String RESULT_URL = "https://fdf5-1-160-3-119.ngrok-free.app/payment/result";
    private static final String MERCHANT_ID = "2000132";
    private static final String HASH_KEY = "5294y06JbISpM5x9";
    private static final String HASH_IV = "v77hoKGq4kWxNNIS";

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentWorkflowService appointmentWorkflowService;
    
    public String buildEcpayForm(Long appointmentId) throws Exception {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            String id = "carePlus0522id" + appointment.getAppointmentId();
            System.out.println("MerchantTradeNo: " + id);  // 確認是否正確生成
            String name = "Caregiver Service";
            String total = appointment.getTotalPrice().setScale(0, RoundingMode.DOWN).toString();
            String desc = "Care+ Appointment EcPayment";
            String date = "2025/05/13 06:05:23";
            Map<String, String> parameters = this.createEcpayData(id, name, total, desc, date);
            
            
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
        
        String checkMacValue = genCheckMacValue(parameters, HASH_KEY, HASH_IV);
        parameters.put("CheckMacValue", checkMacValue);
        return parameters;
    }
    
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

    private String urlEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String hash(byte[] data, String mode){
        try{
            MessageDigest md = MessageDigest.getInstance(mode);
            return bytesToHex(md.digest(data));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    
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
