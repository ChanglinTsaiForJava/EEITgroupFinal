////package eeit.OldProject.rita.Service;
////
////import eeit.OldProject.rita.Repository.AppointmentRepository;
////import eeit.OldProject.rita.Repository.PaymentRepository;
////import lombok.RequiredArgsConstructor;
////import org.springframework.stereotype.Service;
////import eeit.OldProject.rita.Entity.Appointment;
////import eeit.OldProject.rita.Entity.Payment;
////import jakarta.transaction.Transactional;
////import org.springframework.beans.factory.annotation.Value;
////import java.net.URLEncoder;
////import java.nio.charset.StandardCharsets;
////import java.time.LocalDateTime;
////import java.time.format.DateTimeFormatter;
////import java.util.HashMap;
////import java.util.Map;
////import java.util.Optional;
////import java.util.stream.Collectors;
////
////import com.google.common.hash.Hashing;
////
////    @Service
////    @RequiredArgsConstructor
////    public class PaymentService {
////
////        private final AppointmentRepository appointmentRepository;
////        private final PaymentRepository paymentRepository;
////        private final EmailTemplateService emailTemplateService;
////
////        @Value("${ecpay.merchant-id}")
////        private String merchantId;
////
////        @Value("${ecpay.hash-key}")
////        private String hashKey;
////
////        @Value("${ecpay.hash-iv}")
////        private String hashIV;
////
////        @Value("${ecpay.payment-url}")
////        private String paymentUrl;
////
////        @Value("${ecpay.return-url}")
////        private String returnUrl;
////
////        @Transactional
////        public Map<String, String> generatePaymentRequest(Long appointmentId) {
////            Appointment appointment = appointmentRepository.findById(appointmentId)
////                    .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentId));
////
////            if (!appointment.isContractConfirmed()) {
////                throw new IllegalStateException("Appointment contract not confirmed yet.");
////            }
////
////            // 建立 Payment（如果還沒有）
////            Payment payment = paymentRepository.findByAppointmentId(appointmentId)
////                    .orElseGet(() -> {
////                        Payment newPayment = Payment.builder()
////                                .appointmentId(appointmentId)
////                                .amount(appointment.getTotalAmount()) // 你要確保 Appointment 有 getTotalAmount()
////                                .merchantTradeNo(generateMerchantTradeNo(appointmentId))
////                                .status("Pending")
////                                .createdAt(LocalDateTime.now())
////                                .build();
////                        return paymentRepository.save(newPayment);
////                    });
////
////            Map<String, String> params = new HashMap<>();
////            params.put("MerchantID", merchantId);
////            params.put("MerchantTradeNo", payment.getMerchantTradeNo());
////            params.put("MerchantTradeDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
////            params.put("PaymentType", "aio");
////            params.put("TotalAmount", String.valueOf(payment.getAmount().intValue()));
////            params.put("TradeDesc", "Appointment Payment");
////            params.put("ItemName", "Caregiving Service");
////            params.put("ReturnURL", returnUrl);
////            params.put("ChoosePayment", "Credit");
////            params.put("EncryptType", "1");
////
////            String checkMacValue = generateCheckMacValue(params);
////            params.put("CheckMacValue", checkMacValue);
////
////            return params;
////        }
////
////        @Transactional
////        public String handlePaymentCallback(Map<String, String> callbackParams) {
////            String merchantTradeNo = callbackParams.get("MerchantTradeNo");
////            String rtnCode = callbackParams.get("RtnCode"); // 1 = 成功
////            String tradeAmt = callbackParams.get("TradeAmt");
////
////            Optional<Payment> optionalPayment = paymentRepository.findByMerchantTradeNo(merchantTradeNo);
////            if (optionalPayment.isEmpty()) {
////                return "0|FAIL"; // 回傳給綠界：失敗
////            }
////
////            Payment payment = optionalPayment.get();
////            Appointment appointment = appointmentRepository.findById(payment.getAppointmentId())
////                    .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
////
////            // 驗證金額
////            if (!payment.getAmount().equals(new java.math.BigDecimal(tradeAmt))) {
////                return "0|FAIL"; // 金額不符
////            }
////
////            if ("1".equals(rtnCode)) {
////                // 成功
////                payment.setStatus("Paid");
////                appointment.setPaid(true); // 你需要在 Appointment 加一個 paid 欄位（boolean）
////                appointmentRepository.save(appointment);
////                paymentRepository.save(payment);
////
////                // 發送Email通知
////                emailTemplateService.sendPaymentSuccessEmail(appointment.getUser().getEmail(), appointment);
////
////                return "1|OK"; // 綠界要求成功要回 "1|OK"
////            } else {
////                payment.setStatus("Failed");
////                paymentRepository.save(payment);
////                return "0|FAIL";
////            }
////        }
////
////        private String generateMerchantTradeNo(Long appointmentId) {
////            return "AP" + appointmentId + System.currentTimeMillis();
////        }
////
////        private String generateCheckMacValue(Map<String, String> params) {
////            Map<String, String> filteredParams = new HashMap<>(params);
////            filteredParams.remove("CheckMacValue");
////
////            String paramString = filteredParams.entrySet().stream()
////                    .sorted(Map.Entry.comparingByKey())
////                    .map(entry -> entry.getKey() + "=" + entry.getValue())
////                    .collect(Collectors.joining("&"));
////
////            String data = "HashKey=" + hashKey + "&" + paramString + "&HashIV=" + hashIV;
////
////            String urlEncodedData = URLEncoder.encode(data, StandardCharsets.UTF_8)
////                    .replace("+", "%20")
////                    .replace("%21", "!")
////                    .replace("%27", "'")
////                    .replace("%28", "(")
////                    .replace("%2A", "*");
////
////            return Hashing.sha256()
////                    .hashString(urlEncodedData, StandardCharsets.UTF_8)
////                    .toString()
////                    .toUpperCase();
////        }
////    }
//
//package eeit.OldProject.rita.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import eeit.OldProject.rita.Entity.Appointment;
//import eeit.OldProject.rita.Repository.AppointmentRepository;
//import eeit.OldProject.rita.Repository.PaymentRepository;
//
//@Service
//public class PaymentService {
//
//    @Value("${ecpay.merchant-id}")
//    private String merchantId;
//
//    @Value("${ecpay.hash-key}")
//    private String hashKey;
//
//    @Value("${ecpay.hash-iv}")
//    private String hashIV;
//
//    @Value("${ecpay.payment-url}")
//    private String paymentUrl;
//
//    @Value("${ecpay.return-url}")
//    private String returnUrl;
//
//    private final PaymentRepository paymentRepository;
//    private final AppointmentRepository appointmentRepository;
//
//    public PaymentService(PaymentRepository paymentRepository, AppointmentRepository appointmentRepository) {
//        this.paymentRepository = paymentRepository;
//        this.appointmentRepository = appointmentRepository;
//    }
//
//    public Map<String, String> generatePayment(Appointment appointment) {
//        Map<String, String> params = new HashMap<>();
//        // 填入 ECPay 的相關參數
//        params.put("MerchantID", merchantId);
//        params.put("MerchantOrderNo", String.valueOf(appointment.getAppointmentId())); // 這裡用 appointmentId 作為訂單號
//        params.put("Amt", String.valueOf(appointment.getTotalPrice()));
//        params.put("ItemDesc", appointment.getItemDesc());
//        params.put("ReturnURL", returnUrl);
//        
//        // 其他所需的參數
//        // 計算 HashValue
//        String hashValue = generateHashValue(params);
//        params.put("CheckMacValue", hashValue);
//
//        return params;
//    }
//
//    private String generateHashValue(Map<String, String> params) {
//        // 使用 ECPay 提供的計算 HashValue 的方法
//        // 這通常是將參數排序並加上 HashKey 和 HashIV，然後經過 SHA256 等算法
//        return "生成的Hash值";
//    }
//}
//
