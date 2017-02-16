package Controllers;

import Entity.SchoolMoveOrder;
import Entity.SendExpressOrder;
import Service.ManagerService;
import Service.NoticeService;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/10/5.
 * 用来接收消息通知的controller
 */
@Controller
@RequestMapping("/")
public class ReceiveNotify {
    @Resource
    ManagerService managerService;
    @Resource
    NoticeService noticeService;

    @RequestMapping("notify_school_move_paid")
    @ResponseBody
    public String notify_school_move_paid(@RequestParam String orderKey,@RequestParam String validate){
        System.out.println("SchoolMove Notify:"+orderKey+",validate"+validate);
        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
            return "false";

        SchoolMoveOrder smo = managerService.getSchoolMoveOrderByKey(orderKey);
        if(smo == null)
            return "false";

        if(smo.isHaspay()){
            JSONObject data = new JSONObject();
            data.put("name",smo.getName()+","+smo.getPhone()+","+smo.getMoveTime());
            noticeService.CommonSMSSend("SMS_47515056",smo.getPhone(),data);
            noticeService.ReservationService(
                    "服务人员已接单",
                    "小骨头的服务人员将主动联系您",
                    "校园搬运",
                    TimeFormat.format(System.currentTimeMillis()),
                    smo.getName(),
                    "按实际收取",
                    "请耐心等待服务",
                    smo.getOpen_id(),
                    "http://xiaogutou.qdxiaogutou.com/user/index.do");
        }
        return "success";
    }

    @RequestMapping("notify_send_express_paid")
    @ResponseBody
    public String notify_send_express_paid(@RequestParam String orderKey,@RequestParam String validate){

        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
            return "false";

        SendExpressOrder seo = managerService.getSendExpressOrderByKey(orderKey);
        if(seo == null)
            return "false";

        if(seo.isHaspay()){
            JSONObject data = new JSONObject();
            data.put("where",seo.getAddress());
            data.put("name",seo.getName()+seo.getPhone());
            noticeService.CommonSMSSend("SMS_46215163",seo.getExpPhone(),data);
            noticeService.ReservationService(
                    "上门取件已接单",
                    seo.getExpress()+"的服务人员将主动联系您",
                    "上门取件",
                    TimeFormat.format(System.currentTimeMillis()),
                    seo.getName(),
                    (double)seo.getShouldPay()/100+"元",
                    "请耐心等待服务",
                    seo.getOpen_id(),
                    "http://xiaogutou.qdxiaogutou.com/user/index.do");
        }


        return "success";
    }
}
