package eeit.OldProject.rita.Controller;

import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecpay.payment.integration.exception.EcpayException;
import eeit.OldProject.rita.Entity.Appointment;
import eeit.OldProject.rita.Repository.AppointmentRepository;
import eeit.OldProject.rita.Service.AppointmentWorkflowService;
import eeit.OldProject.rita.ecpay.payment.integration.AllInOne;
import eeit.OldProject.rita.ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AppointmentWorkflowService appointmentWorkflowService;
	
	
	@GetMapping("/ecpay")
	public String processPayment(@RequestParam Long appointmentId) {
		
		Optional<Appointment> optionalAppointment =   appointmentRepository.findById(appointmentId);
		
		if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            
		//創建ecpay支付請求
		AllInOne all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();

		obj.setMerchantTradeNo("carePlus0522id"+appointment.getAppointmentId());

		obj.setMerchantTradeDate("2025/05/06 06:05:23");

		obj.setTotalAmount(appointment.getTotalPrice().setScale(0, RoundingMode.DOWN).toString());

		obj.setTradeDesc("Care+ Appointment EcPayment");

		obj.setItemName("Caregiver Service");
		
		
		//回傳URl
		obj.setReturnURL("https://e456-106-104-84-183.ngrok-free.app/payment/callback");
		obj.setNeedExtraPaidInfo("N");
		
		

		try {
            String form = all.aioCheckOut(obj, null);
            return form;  // 返回生成的表单，通常用于显示支付页面
        } catch (EcpayException e) {
            e.printStackTrace();  // 打印异常的详细信息
            return "Error: " + e.getMessage();  // 返回错误信息
        }
    } else {
        return "Appointment not found.";  // 如果预约 ID 无效，返回找不到预约信息的错误
    }
	}
	
	
	@PostMapping("/callback")
	public ResponseEntity<String> payAppointment(HttpServletRequest request) {
	    // 獲取 ECPay 回傳的參數
	    Map<String, String[]> paramMap = request.getParameterMap();
	    Map<String, String> params = paramMap.entrySet().stream()
	            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

	    // 從回傳參數中獲取商戶訂單號（即預約 ID）
	    String appointmentIdStr = params.get("MerchantTradeNo").replace("carePlus0522id", "");  // 假設商戶訂單號包含 appointmentId
	    Long appointmentId = Long.parseLong(appointmentIdStr);
	    
	   // 確認回調資料是否正確進來
	    System.out.println("Received callback for appointmentId: " + appointmentId);
	    
	    
	    // 使用 appointmentId 查找對應的 Appointment 資料
	    Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

	    if (optionalAppointment.isPresent()) {
	        Appointment appointment = optionalAppointment.get();
	        
	     // 確認支付結果是否成功
            String paymentStatus = params.get("RtnCode");  // ECPay 回傳的支付狀態
            if ("1".equals(paymentStatus)) {  // 假設支付成功的回應碼為 1
	        try {
	            // 呼叫 markAsPaid 方法來更新預約狀態並發送通知
	            appointmentWorkflowService.markAsPaid(appointment.getAppointmentId());
	            return ResponseEntity.ok("Payment status updated to 'Paid'");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking appointment as paid: " + e.getMessage());
	        }
	    } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Payment failed. Payment status: " + paymentStatus);
        }
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
    }
	}

}

///**顧客付款完成（模擬 ECPay callback）**/
//@PutMapping("/{id}/pay")
//public ResponseEntity<Appointment> payAppointment(@PathVariable Long id) {
//  try {
//      return ResponseEntity.ok(appointmentWorkflowService.markAsPaid(id));
//  } catch (ChangeSetPersister.NotFoundException e) {
//      return ResponseEntity.notFound().build();
//  }
//}