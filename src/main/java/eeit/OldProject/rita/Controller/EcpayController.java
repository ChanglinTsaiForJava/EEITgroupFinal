package eeit.OldProject.rita.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eeit.OldProject.rita.Service.EcpayFunction;

@Controller
@RequestMapping("/payment")
public class EcpayController {
    @Autowired
    private EcpayFunction ecpayFunction;
    
    @PostMapping("/ecpay")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<String> processPayment(@RequestParam Long appointmentId) {
        try {
            String form = ecpayFunction.buildEcpayForm(appointmentId);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating payment form: " + e.getMessage());
        }
    }
    
//    @PostMapping("/callback")
//    public ResponseEntity<String> handlePaymentCallback(@RequestBody String body) {
//        System.out.println("Received callback body: " + body);
//        return ResponseEntity.ok("Callback received.");
//    }
//    
//    @PostMapping("/result")
//    public ResponseEntity<String> handlePaymentResult(@RequestBody String body) {
//        System.out.println("Received result body: " + body);
//        return ResponseEntity.ok("Result received.");
//    }
    
 // 用於瀏覽器重導向的 Result URL
    @PostMapping("/result")
    @CrossOrigin(origins = "*")
    public String ecpayResult(@RequestBody String body) {
        System.out.println("ecpay result2 " + System.currentTimeMillis());
        System.out.println("body=" + body);
        
        // 這裡應該使用 302 重導向
//        return "redirect:http://localhost:5173/payment/success";
        return "redirect:https://tw.yahoo.com";

    }

    // 用於伺服器之間的通知 (Server to Server)
    @PostMapping("/return")
    public String ecpayReturn(@RequestBody String body) {
        System.out.println("ecpay return1 " + System.currentTimeMillis());
        System.out.println("body=" + body);
        
        // 直接回傳 OK，告訴 ECPay 已經處理完成
        return "";
    }
    
//    @PostMapping("/send")
//    @ResponseBody
//    @CrossOrigin
//    public String send(@RequestParam Long appointmentId) {
//    	try {
//            String form = ecpayFunction.buildEcpayForm(appointmentId);
//            return form;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }
}
