package eeit.OldProject.rita.Controller;

import eeit.OldProject.rita.Service.AppointmentWorkflowService;
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

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class EcpayController {

    @Autowired
    private EcpayFunction ecpayFunction;
    @Autowired
    private AppointmentWorkflowService appointmentWorkflowService;

    /**
     * ✅ 按下「立即付款」時
     * 根據AppointmentId 產生綠界付款表單（HTML）並返回給前端，
     * 前端收到 HTML 表單後會自動送出導向綠界金流畫面。
     *
     * @param appointmentId 預約單 ID
     * @return HTML Form 字串（會自動 submit 至綠界）
     */
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

    /**
     * ✅ 綠界付款完成後，會導回這個 API
     * 「ResultURL」：使用者付款完成後，瀏覽器畫面會跳回這裡。
     * @param body 綠界 POST 回傳的資訊（可忽略）
     * @return 302 redirect 到任意網址
     */
    @PostMapping("/result")
    @CrossOrigin(origins = "*")
    public String ecpayResult(@RequestBody String body) {
        System.out.println("ecpay result2 " + System.currentTimeMillis());
        System.out.println("body=" + body);

        return "redirect:https://tw.yahoo.com";

    }

    /**
     * ✅ 綠界後台通知（Server to Server）
     * 「ReturnURL」：綠界會在付款成功後呼叫markAsPaid
     *  更新訂單狀態為「已付款」。
     *  流程：
     *  1. 解析 merchantTradeNo 中的 appointmentId
     *  2. 如果付款成功（RtnCode = 1）就呼叫 service 更新狀態
     *
     * @param params 綠界回傳的所有參數（包含 MerchantTradeNo, RtnCode 等）
     * @return 必須回傳 "1|OK" 告訴綠界你成功收到並處理
     */
    @PostMapping("/return")
    @ResponseBody
    public String ecpayReturn(@RequestParam Map<String, String> params) {
        System.out.println("✅ 綠界 return 成功收到：");
        params.forEach((k, v) -> System.out.println(k + " = " + v));
        String merchantTradeNo = params.get("MerchantTradeNo"); // carePlus05222id3
        String rtnCode = params.get("RtnCode"); // 成功為 1

        if ("1".equals(rtnCode) && merchantTradeNo != null && merchantTradeNo.startsWith("carePlus05222id")) {
            try {
                Long appointmentId = Long.valueOf(merchantTradeNo.replace("carePlus05222id", ""));
                appointmentWorkflowService.markAsPaid(appointmentId); //更新為已付款
                System.out.println("✅ 已將 appointmentId " + appointmentId + " 設為 Paid");
            } catch (Exception e) {
                System.out.println("❌ 無法解析 appointmentId 或更新失敗：" + e.getMessage());
            }
        }

        return "1|OK";
    }

//    // 用於伺服器之間的通知 (Server to Server)
//    @PostMapping("/return") 舊有
//    public String ecpayReturn(@RequestBody String body) {
//        System.out.println("ecpay return1 " + System.currentTimeMillis());
//        System.out.println("body=" + body);
//
//        // 直接回傳 OK，告訴 ECPay 已經處理完成
//        return "";
//    }


}
