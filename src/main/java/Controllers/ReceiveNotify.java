package Controllers;

import Entity.SchoolConfigs;
import Entity.SchoolMoveOrder;
import Entity.SendExpressOrder;
import Service.ManagerService;
import Service.NoticeService;
import Service.UserService;
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
    UserService userService;
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
            data.put("name",smo.getName()+","+smo.getPhone());
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(smo.getSchoolId());
            noticeService.CommonSMSSend("SMS_47515056",String.valueOf(sc.getServicePhone()),data);
            noticeService.ReservationService(
                    "服务人员已接单",
                    "小骨头的服务人员将主动联系您",
                    "校园搬运",
                    TimeFormat.format(System.currentTimeMillis()),
                    "骨头小哥",
                    "按实际收取",
                    "请耐心等待服务",
                    smo.getOpen_id(),
                    "http://xiaogutou.qdxiaogutou.com/user/index.do");

            //管理分红
            managerService.managerDividend(sc.getSchoolId(),500,smo.getId(),"校园搬运订单分红");
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
                    "快递小哥",
                    (double)seo.getShouldPay()/100+"元",
                    "请耐心等待服务",
                    seo.getOpen_id(),
                    "http://xiaogutou.qdxiaogutou.com/user/index.do");

            //管理分红
            managerService.managerDividend(seo.getSchoolId(),seo.getShouldPay(),seo.getId(),"代寄快递分红");
        }


        return "success";
    }
}
