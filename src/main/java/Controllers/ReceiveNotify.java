package Controllers;

import Entity.SchoolMoveOrder;
import Entity.SendExpressOrder;
import Utils.MD5;
import Utils.TimeFormat;
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
//    @Resource
//    ManagerService managerService;

//    @RequestMapping("notify_school_move_paid")
//    @ResponseBody
//    public String notify_school_move_paid(@RequestParam String orderKey,@RequestParam String validate){
//        System.out.println("SchoolMove Notify:"+orderKey+",validate"+validate);
//        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
//            return "false";
//
//        SchoolMoveOrder smo = managerService.getPaidShoolMoveOrder(orderKey);
//        if(smo == null)
//            return "false";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("OpenId:"+smo.getOpen_id());
//        sb.append("<br>下单时间:"+ TimeFormat.format(smo.getOrderTime()));
//        sb.append("<br>姓名:"+smo.getName());
//        sb.append("<br>联系电话(点击拨打):<a href=\"tel:"+smo.getPhone()+"\">"+smo.getPhone()+"</a>");
//        sb.append("<br>用车时间:"+smo.getMoveTime());
//        sb.append("<br>状态：已支付。");
//        String content =  sb.toString();
//
//        return "success";
//    }

//    @RequestMapping("notify_send_express_paid")
//    @ResponseBody
//    public String notify_send_express_paid(@RequestParam String orderKey,@RequestParam String validate){
//
//        if(!MD5.encryption("binghai"+orderKey+"binghai").equals(validate))
//            return "false";
//
//        SendExpressOrder seo = managerService.getSendExpressOrder(orderKey);
//        if(seo == null)
//            return "false";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("OpenId:"+seo.getOpen_id());
//        sb.append("<br>下单时间:"+ TimeFormat.format(seo.getOrderTime()));
//        sb.append("<br>姓名:"+seo.getName());
//        sb.append("<br>联系电话(点击拨打):<a href=\"tel:"+seo.getPhone()+"\">"+seo.getPhone()+"</a>");
//        sb.append("<br>代寄快递:"+seo.getExpress());
//        sb.append("<br>取件地址:"+seo.getAddress());
//        sb.append("<br>状态：已支付。");
//        String content =  sb.toString();
//
//        return "success";
//    }
}
