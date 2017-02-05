package Controllers.Manager;

import Entity.ExpressOrder;
import Entity.Manager.PayLog;
import Service.ManagerService;
import Utils.FailedAnswer;
import Utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/1/9.
 */
@Controller
@RequestMapping("ajax")
public class Ajax {

    @Resource
    ManagerService managerService;

    /**
     * 接单
     * */
    @RequestMapping("take_order")
    @ResponseBody
    public String ajax_take_order(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId){
        return String.valueOf(managerService.managerTakeOrder(managerId,schoolId,orderId));
    }

    /**
     * 取件成功
     * */
    @RequestMapping("fetch_order")
    @ResponseBody
    public String fetch_order(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId){
        /**
         * 管理分红、分取件费
         * */
        ExpressOrder order = managerService.getExpressOrderById(orderId);
        if(order == null) return "false";
        managerService.managerDividend(schoolId,order.getShouldPay(),orderId);
        managerService.rewardFetchOrder(schoolId,managerId,orderId);
        return String.valueOf(managerService.managerFetchOrder(managerId,schoolId,orderId));
    }

    @RequestMapping("update_courier_number")
    @ResponseBody
    public String update_courier_number(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId,
            @RequestParam String data){

        return String.valueOf(managerService.update_courier_number(managerId,schoolId,orderId,data));
    }

    /**
     * 更新配送结果
     * */
    @RequestMapping("express_order_send_result")
    @ResponseBody
    public String express_order_send_result(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId,
            @RequestParam boolean result,
            @RequestParam int reasonId){

        if(result)
            /** 赏配送费 注意判别是否是楼长交接件，如果是，则不再分配*/
            managerService.rewardSendOrder(managerId,schoolId,orderId);
        return String.valueOf(
                managerService.updateExpressOrderResultReason(managerId,schoolId,orderId,result? ExpressOrder.SEND_SUCCESS:ExpressOrder.ORDER_SEND_FAILED,reasonId)
        );
    }

    @RequestMapping("privilege_changed")
    @ResponseBody
    public String privilege_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int privilegeId){
        if(!managerService.managerAccess2Privilege(managerId,"qxgl"))
            return "false";
        if (managerService.managerAccess2PrivilegeId(mid,privilegeId))
            managerService.revokePrivilege(mid,privilegeId);
        else
            managerService.grantPrivilege(mid,privilegeId);
        return "true";
    }
    @RequestMapping("school_manager_changed")
    @ResponseBody
    public String school_manager_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int sid){
        if (managerService.managerAccess2Privilege(managerId,"xxgl")){
            if(managerService.managerAccess2School(mid,sid))
                managerService.deleteSchoolManager(sid,mid);
            else
                managerService.addSchoolManager(sid,mid);
            return "true";
        }
        return "false";
    }
    @RequestMapping("delete_reason")
    @ResponseBody
    public String delete_reason(@RequestParam int managerId,
                                    @RequestParam int rid){
        if (managerService.managerAccess2Privilege(managerId,"reason_manage")){
            managerService.deleteReason(rid);
            return "true";
        }
        return "false";
    }

    @RequestMapping("delete_manager")
    @ResponseBody
    public String delete_manager(@RequestParam int managerId,
                                    @RequestParam int mid){
        if (managerService.managerAccess2Privilege(managerId,"work_group")){
            managerService.deleteManager(mid);
            return "true";
        }
        return "false";
    }

    @RequestMapping("pay_to_p")
    @ResponseBody
    public String pay2p(@RequestParam int managerId, @RequestParam int orderId, HttpSession session){
        JSONObject rs = new JSONObject();
        if(managerService.managerAccess2Privilege(managerId,"gzqs")){
            PayLog p = managerService.getPayLogById(orderId);
            if(p == null) return "参数错误，非法调用";
            if(p.isHasPay()) return "该订单已支付，无需重复支付";
            String url = session.getServletContext().getInitParameter("pay_url");
            String pass = session.getServletContext().getInitParameter("pay_pwd");
            p.makePass(pass);
            // 发起支付
            String res = HttpUtils.sendPost(url,"passwd="+p.getPasswd()+"&orderid="+p.getTradeNo()+"&amount="+p.getAmount()+"&openid="+p.getOpenId()+"&desc="+p.getPdesc());
            if(res.equals("result_code:SUCCESS")){
                // 支付成功，标记
                p.setHasPay(true);
                managerService.update(p);
                rs.put("result","支付成功");
                return rs.toJSONString();
            }else{
                // 支付失败，更换支付单号
                p.reMakeTradeNo();
                managerService.update(p);
                rs.put("result",res);
                return rs.toJSONString();
            }
        }

        rs.put("result","财务服务器出了一点问题...请稍后重试");
        return rs.toJSONString();
    }
}
