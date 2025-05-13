//package eeit.OldProject.rita.Controller;
//
//import java.math.RoundingMode;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import ecpay.payment.integration.AllInOne;
//import ecpay.payment.integration.domain.AioCheckOutALL;
//import ecpay.payment.integration.exception.EcpayException;
//import eeit.OldProject.rita.Entity.Appointment;
//import eeit.OldProject.rita.Repository.AppointmentRepository;
//import eeit.OldProject.rita.Service.AppointmentWorkflowService;
//import jakarta.servlet.http.HttpServletRequest;
//import eeit.OldProject.rita.Service.EcpayFunction;
//
//
//@RestController
//@RequestMapping("/payment")
//public class PaymentController {
//	
//	
//	@Autowired
//	private AppointmentRepository appointmentRepository;
//	@Autowired
//	private AppointmentWorkflowService appointmentWorkflowService;
//	
//	
//	@PostMapping("/ecpay")
//	public String processPayment(@RequestParam Long appointmentId) {
//		
//		System.out.println("Received request data: " + appointmentId);
//		 
//		Optional<Appointment> optionalAppointment =   appointmentRepository.findById(appointmentId);
//		
//		if (optionalAppointment.isPresent()) {
//            Appointment appointment = optionalAppointment.get();
//            
//		//創建ecpay支付請求
//		AllInOne all = new AllInOne("");
//		AioCheckOutALL obj = new AioCheckOutALL();
//
//		obj.setMerchantTradeNo("carePlus0522id"+appointment.getAppointmentId());
//        
//		obj.setMerchantTradeDate("2025/05/13 06:05:23");
//
//		obj.setTotalAmount(appointment.getTotalPrice().setScale(0, RoundingMode.DOWN).toString());
//
//		obj.setTradeDesc("Care+ Appointment EcPayment");
//
//		obj.setItemName("Caregiver Service");
//		
////		obj.setClientBackURL("https://4226-1-160-3-119.ngrok-free.app/");
//		
//		//回傳URl
//		obj.setReturnURL("https://0448-1-160-3-119.ngrok-free.app/payment/callback");
//		obj.setNeedExtraPaidInfo("N");
//		
//		
//
//		try {
//            String form = all.aioCheckOut(obj, null);
//            return form;  // 返回生成的表单，通常用于显示支付页面
//        } catch (EcpayException e) {
//            e.printStackTrace();  // 打印异常的详细信息
//            return "Error: " + e.getMessage();  // 返回错误信息
//        }
//    } else {
//        return "Appointment not found.";  // 如果预约 ID 无效，返回找不到预约信息的错误
//        
//    }
//	}
//	
//	@PostMapping("/callback")
//	public ResponseEntity<String> payAppointment(HttpServletRequest request) {
//	    // 獲取 ECPay 回傳的參數
//	    Map<String, String[]> paramMap = request.getParameterMap();
//	    Map<String, String> params = paramMap.entrySet().stream()
//	            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
//	    
//	    System.out.println("Received callback params: " + params);
//
//	    // 從回傳參數中獲取商戶訂單號（即預約 ID）
//	    String appointmentIdStr = params.get("MerchantTradeNo").replace("carePlus0522id", "");  // 假設商戶訂單號包含 appointmentId
//	    Long appointmentId = Long.parseLong(appointmentIdStr);
//
//	    // 確認回調資料是否正確進來
//	    System.out.println("Received callback for appointmentId: " + appointmentId);
//
//	    // 使用 appointmentId 查找對應的 Appointment 資料
//	    Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
//
//	    if (optionalAppointment.isPresent()) {
//            System.out.println("Appointment not found for appointmentId: " + appointmentId);
//
//	        Appointment appointment = optionalAppointment.get();
//	        System.out.println("Appointment found: " + appointment);
//
//
//	        // 確認支付結果是否成功
//	        String paymentStatus = params.get("RtnCode");  // ECPay 回傳的支付狀態
//	        System.out.println("Payment status: " + paymentStatus); // 打印支付狀態
//
//	        if ("1".equals(paymentStatus)) {  // 假設支付成功的回應碼為 1
//	            try {
//	                // 呼叫 markAsPaid 方法來更新預約狀態並發送通知
//	                appointmentWorkflowService.markAsPaid(appointment.getAppointmentId());
//	                System.out.println("Payment success, appointment marked as paid.");
//
//	                // 成功後重定向到前端的成功頁面
//	                return ResponseEntity.status(HttpStatus.FOUND)
//	                        .header("Location", "http://localhost:5173/payment/success")  // 重定向到前端的成功頁面
//	                        .build();
//	            } catch (Exception e) {
//	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking appointment as paid: " + e.getMessage());
//	            }
//	        } else {
//	            // 如果支付未成功，不做任何處理，只是返回 OK（可以視情況調整）
//	            System.out.println("Payment failed, no action taken.");
//
//	            return ResponseEntity.ok("Payment failed, but no action is taken.");
//	        }
//	    } else {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
//	    }
//	}
//}
//	
//
//
/////**顧客付款完成（模擬 ECPay callback）**/
////@PutMapping("/{id}/pay")
////public ResponseEntity<Appointment> payAppointment(@PathVariable Long id) {
////  try {
////      return ResponseEntity.ok(appointmentWorkflowService.markAsPaid(id));
////  } catch (ChangeSetPersister.NotFoundException e) {
////      return ResponseEntity.notFound().build();
////  }
////}