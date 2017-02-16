package Controllers.Manager;

import Entity.*;
import Entity.Manager.Manager;
import Entity.Manager.PayLog;
import Entity.User.User;
import Service.ManagerService;
import Service.NoticeService;
import Service.UserService;
import Utils.FailedAnswer;
import Utils.HttpUtils;
import Utils.MD5;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Resource
    NoticeService noticeService;
    @Resource
    UserService userService;

    /**
     * 接单
     * */
    @RequestMapping("take_order")
    @ResponseBody
    public String ajax_take_order(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId){
        managerService.log(managerId,11,"接单单号:"+orderId);
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

        /** 赏配送费 注意判别是否是楼长交接件，如果是，则不再分配*/
        if(result){
            managerService.log(managerId,11,orderId+"订单准备赏配送费");
            managerService.rewardSendOrder(managerId,schoolId,orderId);
        }else{
            managerService.log(managerId,11,orderId+"订单不能赏配送费，因为result=false");
        }


        return String.valueOf(
                managerService.updateExpressOrderResultReason(managerId,schoolId,orderId,result? ExpressOrder.SEND_SUCCESS:ExpressOrder.ORDER_SEND_FAILED,reasonId)
        );
    }

    /**
     * 用户权限更改
     * */
    @RequestMapping("privilege_changed")
    @ResponseBody
    public String privilege_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int privilegeId){
        if(!managerService.managerAccess2Privilege(managerId,"qxgl"))
            return "false";
        if (managerService.managerAccess2PrivilegeId(mid,privilegeId)){
            managerService.revokePrivilege(mid,privilegeId);
            managerService.log(managerId,11,"撤销对"+mid+"的"+privilegeId+"授权");
        }else{
            managerService.log(managerId,11,"增加对"+mid+"的"+privilegeId+"授权");
            managerService.grantPrivilege(mid,privilegeId);
        }
        return "true";
    }

    /**
     * 更改学校管理员
     * */
    @RequestMapping("school_manager_changed")
    @ResponseBody
    public String school_manager_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int sid){
        if (managerService.managerAccess2Privilege(managerId,"xxgl")){
            if(managerService.managerAccess2School(mid,sid)){
                managerService.log(managerId,11,"删除"+sid+"学校管理员"+mid);
                managerService.deleteSchoolManager(sid,mid);
            }else{
                managerService.log(managerId,11,"添加"+sid+"学校管理员"+mid);
                managerService.addSchoolManager(sid,mid);
            }
            return "true";
        }
        return "false";
    }

    /**
     * 删除原因
     * */
    @RequestMapping("delete_reason")
    @ResponseBody
    public String delete_reason(@RequestParam int managerId,
                                    @RequestParam int rid){
        if (managerService.managerAccess2Privilege(managerId,"reason_manage")){
            managerService.log(managerId,11,"删除原因"+rid);
            managerService.deleteReason(rid);
            return "true";
        }
        return "false";
    }

    /**
     * 删除管理员
     * */
    @RequestMapping("delete_manager")
    @ResponseBody
    public String delete_manager(@RequestParam int managerId,
                                    @RequestParam int mid){
        if (managerService.managerAccess2Privilege(managerId,"work_group")){
            Manager manager = managerService.getManagerById(mid);
            if(manager.isCould_delete()){
                managerService.log(managerId,11,"删除管理员"+mid);
                managerService.deleteManager(mid);
                AppCgi.clearToken(mid);
            }else{
                return "超级用户，不可删除";
            }
            return "true";
        }
        return "false";
    }

    /**
     * 支付工资
     * */
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
                managerService.log(managerId,11,"成功支付工资订单"+orderId);
                return rs.toJSONString();
            }else{
                // 支付失败，更换支付单号
                p.reMakeTradeNo();
                managerService.update(p);
                rs.put("result",res);
                managerService.log(managerId,11,"工资订单"+orderId+"支付失败，"+res);
                return rs.toJSONString();
            }
        }

        rs.put("result","财务服务器出了一点问题...请稍后重试");
        return rs.toJSONString();
    }


    /**
     * 更新工资设置
     * */
    @RequestMapping("update_salary_conf")
    @ResponseBody
    public String update_salary_conf(@RequestParam int pid,@RequestParam double val,@RequestParam int managerId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            if(val>=0 && val <1){
                Manager manager = managerService.getManagerById(pid);
                if(manager == null) return "非法操作";
                manager.setDividendRatio(val);
                managerService.update(manager);
                managerService.log(managerId,11,pid+"工资更新为"+val);
                return "更新成功";
            }else return "输入不规范，请参看说明";
        }
        return "无权限操作";
    }

    /**
     * 手动控制学校
     * */
    @RequestMapping("hand_controll_change")
    @ResponseBody
    public String hand_controll_change(@RequestParam int managerId,
                                       @RequestParam int schoolId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setHand_close(!sc.isHand_close());
            managerService.update(sc);
            managerService.log(managerId,11,schoolId+"学校总控:"+(sc.isHand_close()?"系统已停止接单":"系统正在自动控制"));
            return sc.isHand_close()?"系统已停止接单":"系统正在自动控制";
        }
        return "无权操作";
    }

    /**
     * 控制首单优惠策略
     * */
    @RequestMapping("firstDiscountChange")
    @ResponseBody
    public String firstDiscountChange(@RequestParam int managerId,
                                       @RequestParam int schoolId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setFirstDiscount(!sc.isFirstDiscount());
            managerService.update(sc);
            return sc.isFirstDiscount()?"已启用首单优惠":"已关闭首单优惠";
        }
        return "无权操作";
    }

    /**
     * 控制满十减一策略
     * */
    @RequestMapping("ifTenThenFree")
    @ResponseBody
    public String ifTenThenFree(@RequestParam int managerId,
                                       @RequestParam int schoolId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setIfTenThenFree(!sc.isIfTenThenFree());
            managerService.update(sc);
            return sc.isIfTenThenFree()?"已启用满十减一":"已关闭满十减一";
        }
        return "无权操作";
    }

    /**
     * 控制校园搬运业务
     * */
    @RequestMapping("schoolMove")
    @ResponseBody
    public String schoolMove(@RequestParam int managerId,
                                       @RequestParam int schoolId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setSchoolMove(!sc.isSchoolMove());
            managerService.update(sc);
            return sc.isSchoolMove()?"已开启校园搬运业务":"已关闭校园搬运业务";
        }
        return "无权操作";
    }

    /**
     * 控制代寄快递业务
     * */
    @RequestMapping("helpSend")
    @ResponseBody
    public String helpSend(@RequestParam int managerId,
                                       @RequestParam int schoolId){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setHelpSend(!sc.isHelpSend());
            managerService.update(sc);
            return sc.isHelpSend()?"已开启代寄快递业务":"已关闭代寄快递业务";
        }
        return "无权操作";
    }

    /**
     * 删除某个VIP套餐
     * */
    @RequestMapping("deleteVip")
    @ResponseBody
    public String deleteVip(@RequestParam int managerId,
                            @RequestParam int schoolId,@RequestParam int id){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")
                &&managerService.managerAccess2School(managerId,schoolId)){
            managerService.deleteVipMeal(id);
            managerService.log(managerId,11,"删除VIP套餐"+id);
            return "已删除";
        }
        return "无权操作";
    }


    /**
     * 删除某个代寄快递点
     * */
    @RequestMapping("deleteSendExpress")
    @ResponseBody
    public String deleteSendExpress(@RequestParam int managerId, @RequestParam int id){
        if(managerService.managerAccess2Privilege(managerId,"help_send")){
            managerService.deleteSendExpress(id);
            managerService.log(managerId,11,"删除快递点"+id);
            return "已删除";
        }
        return "无权操作";
    }

    /**
     * 退款操作
     * */
    @RequestMapping("refund")
    @ResponseBody
    public String refund(@RequestParam int managerId,
                         @RequestParam int schoolId,@RequestParam int id,HttpSession session){
        if(managerService.managerAccess2School(managerId,schoolId)){
            ExpressOrder order = managerService.getExpressOrderById(id);
            if(order == null) return "参数错误";
            if(!order.isHas_pay()) return "参数错误";
            if(order.getOrder_state() == -1) return "该订单已经退款，不能再次退款";
            if(order.getOrder_state()!=0 && order.getOrder_state() != 1) return "已进入任务流程，无法退款";
            String pass = session.getServletContext().getInitParameter("refund_pwd");
            String url = session.getServletContext().getInitParameter("refund_url");
            String validate = MD5.encryption(order.getOrderKey()+pass+order.getShouldPay());

            String rs =HttpUtils.sendGet(url,"out_trade_no="+order.getOrderKey()+"&refund_fee="+order.getShouldPay()+"&validate="+validate);

            if(rs.contains("SUCCESS")){
                order.setOrder_state(-1);
                managerService.update(order);
                managerService.orderSumCutOne(order.getUser_id());
                managerService.log(managerId,11,id+"订单退款:"+rs);
                noticeService.refund(order.getId(),order.getUser_id(),order.getShouldPay(),"您的订单退款成功，按原路退回");
            }else if(rs.contains("订单不存在")){ //余额支付订单
                rs="余额支付单，";
                order.setOrder_state(-1);
                managerService.update(order);
                managerService.orderSumCutOne(order.getUser_id());
                boolean res = managerService.refundVipPay(order.getUser_id(),order.getShouldPay());
                rs += (res?"已退款至账户":"但退款时发生错误");
                managerService.log(managerId,11,id+"订单退款:"+rs);
                noticeService.refund(order.getId(),order.getUser_id(),order.getShouldPay(),"您的订单退款结果:"+(res?"已退款至账户余额":"退款时发生错误"));
            }
            return rs;
        }else return "无权操作";
    }
    /**
     * 隐藏/激活某个快递
     * */
    @RequestMapping("exp_status_change")
    @ResponseBody
    public String exp_status_change(@RequestParam int managerId,@RequestParam int eid){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            Express exp = managerService.geteExpressById(eid);
            if(exp == null) return "非法访问";
            exp.setAvailable(!exp.isAvailable());
            managerService.update(exp);
            managerService.log(managerId,11,eid+"快递状态调整:"+(exp.isAvailable()?"已暂停该快递业务":"已开启该快递业务"));
            return exp.isAvailable()?"已开启该快递业务":"已暂停该快递业务";
        }
        return "无权操作";
    }
    /**
     * 隐藏/激活某个配送时间段
     * */
    @RequestMapping("sendtime_changed")
    @ResponseBody
    public String sendtime_changed(@RequestParam int managerId,@RequestParam int id){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            SendTime st = managerService.getSendTimeById(id);
            if(st == null) return "非法访问";
            st.setAvailable(!st.isAvailable());
            managerService.update(st);
            managerService.log(managerId,11,id+"配送时间调整为:"+(st.isAvailable()?"已开启该时段":"已暂停该时段"));
            return st.isAvailable()?"已开启该时段":"已暂停该时段";
        }
        return "无权操作";
    }

    /**
     * 快递改价
     * */
    @RequestMapping("reset_exp_price")
    @ResponseBody
    public String reset_exp_price(@RequestParam int managerId,@RequestParam int eid,@RequestParam int price){
        if(managerService.managerAccess2Privilege(managerId,"xtsz")){
            Express exp = managerService.geteExpressById(eid);
            if(exp == null) return "非法访问";
            if(price<1) return "费用输入错误";
            managerService.log(managerId,11,eid+"快递从"+exp.getSendPrice()+"改价为"+price);
            exp.setSendPrice(price);
            managerService.update(exp);

            return "费用已设置为:"+(double)exp.getSendPrice()/100+"元";
        }
        return "无权操作";
    }
}
