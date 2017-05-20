package Controllers;

import Entity.ExpressOrder;
import Entity.Manager.AppPushMsg;
import Entity.Manager.Manager;
import Entity.SchoolConfigs;
import Entity.SchoolMoveOrder;
import Entity.SendExpressOrder;
import Entity.User.User;
import Service.ManagerService;
import Service.NoticeService;
import Service.UserService;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

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


    @RequestMapping(value = "notify_school_move_paid",method = RequestMethod.POST)
    @ResponseBody
    public String notify_school_move_paid(@RequestParam String orderKey,@RequestParam String validate){
        System.out.println("SchoolMove Notify:"+orderKey+",validate"+validate);
        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
            return "false";

        SchoolMoveOrder smo = managerService.getSchoolMoveOrderByKey(orderKey);
        if(smo == null)
            return "false";

        if(smo.isHaspay()){
            income_add(smo.getSchoolId(),500);
            JSONObject data = new JSONObject();
            data.put("name",smo.getName()+","+smo.getPhone());
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(smo.getSchoolId());
            //noticeService.CommonSMSSend("SMS_47515056",String.valueOf(sc.getServicePhone()),data);
            noticeService.NoticeManagers(sc.getSchoolId(),"新校园搬运订单",smo.getName()+","+smo.getMoveTime()+","+smo.getPhone(),"校园搬运订单",5.0);
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

    @RequestMapping(value = "notify_send_express_paid",method = RequestMethod.POST)
    @ResponseBody
    public String notify_send_express_paid(@RequestParam String orderKey,@RequestParam String validate){

        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
            return "false";

        SendExpressOrder seo = managerService.getSendExpressOrderByKey(orderKey);
        if(seo == null)
            return "false";

        if(seo.isHaspay()){
            income_add(seo.getSchoolId(),seo.getShouldPay());
            JSONObject data = new JSONObject();
            data.put("where",seo.getAddress());
            data.put("name",seo.getName()+seo.getPhone());
//            noticeService.CommonSMSSend("SMS_46215163",seo.getExpPhone(),data);
            noticeService.NoticeManagers(seo.getSchoolId(),seo.getExpress()+"代寄订单",seo.getName()+","+seo.getAddress()+","+seo.getPhone(),"代寄订单",seo.getShouldPay()/100);
            noticeService.ReservationService(
                    "上门取件已接单",
                    seo.getExpress()+"的服务人员将主动联系您",
                    "上门取件",
                    TimeFormat.format(System.currentTimeMillis()),
                    "快递小哥",
                    (double)seo.getShouldPay()/100+"元",
                    "请耐心等待服务",
                    seo.getOpen_id(),
                    "http://xiaogut ou.qdxiaogutou.com/user/index.do");

            //管理分红
            managerService.managerDividend(seo.getSchoolId(),seo.getShouldPay(),seo.getId(),"代寄快递分红");
        }


        return "success";
    }

    @RequestMapping(value = "notify_express_paid",method = RequestMethod.POST)
    public String notify_express_paid(@RequestParam String orderKey,@RequestParam String validate){
        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
            return "false";
        ExpressOrder order = userService.getExpressOrderByOrderKey(orderKey);
        if(order != null)
            if(order.isHas_pay()){
                User user = userService.getUserById(order.getUser_id());
                if(user != null){
                    user.setOrder_sum(user.getOrder_sum()+1);
                    managerService.update(user);
                    income_add(user.getSchoolId(),order.getShouldPay());
                    noticeService.paySuccess("小骨头订单微信支付成功",(double)order.getShouldPay()/100+"元","如有疑问或退款，请点我召唤客服","代取快递",user.getOpen_id(),"http://xiaogutou.qdxiaogutou.com/user/see_order_detail.do?id="+order.getId(),order);
                }
            }
        return "success";
    }



    private void income_add(int sid,int many){
        SchoolConfigs sc = userService.getSchoolConfBySchoolId(sid);
        sc.setSumIncome(sc.getSumIncome()+many);
        managerService.update(sc);
        managerService.log(-1,11,sc.getId()+"学校总收入变为[NotifyModified]:"+sc.getSumIncome());
    }
}
